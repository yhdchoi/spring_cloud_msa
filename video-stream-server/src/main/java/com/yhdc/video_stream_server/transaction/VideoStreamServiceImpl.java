package com.yhdc.video_stream_server.transaction;

import com.yhdc.video_stream_server.transaction.object.CommonErrorBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.List;
import java.util.UUID;

import static com.yhdc.video_stream_server.transaction.type.Constants.VIDEO_BASE_DIR;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoStreamServiceImpl implements VideoStreamService {

    private final VideoCatalogRestClientService videoCatalogRestClientService;
    private final ResourceLoader resourceLoader;

    /**
     * LOAD VIDEO FILE THEN LOAD STREAM
     *
     * @param videoPath
     * @implNote
     */
    @Override
    public ResponseEntity<StreamingResponseBody> getVideoStream(String videoPath) {
        try {
            Resource resource = resourceLoader.getResource(videoPath);
            File file = resource.getFile();
            if (!file.isFile()) {
                log.error("Video file not found path: {}", videoPath);
                return ResponseEntity.notFound().build();
            }

            // Stream video
            StreamingResponseBody streamingResponseBody = outputStream -> {
                try {
                    final InputStream inputStream = new FileInputStream(file);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = inputStream.read(bytes)) >= 0) {
                        outputStream.write(bytes, 0, length);
                    }
                    inputStream.close();
                    outputStream.flush();

                } catch (final Exception e) {
                    log.error("Exception while reading and streaming data", e);
                }
            };

            final HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", "video/mp4");
            responseHeaders.add("Content-Length", Long.toString(file.length()));

            return ResponseEntity.ok().headers(responseHeaders).body(streamingResponseBody);

        } catch (IOException ioe) {
            log.error("Unable to read file from the resource!!!");
            log.error(ioe.toString());
            return ResponseEntity.internalServerError().build();
        }
    }


    /**
     * SAVE PRODUCT VIDEO
     *
     * @param fileArray
     * @implNote Save new video catalog and files for a product
     */
    @Override
    public ResponseEntity<?> saveProductVideos(String userId, String productId, MultipartFile[] fileArray) {

        try {
            final UUID transactionId = UUID.randomUUID();
            final String fullDirectory = VIDEO_BASE_DIR + productId;

            // Video directory check
            File directory = new File(fullDirectory);
            if (!directory.exists()) {
                boolean isMkdir = directory.mkdirs();
                if (!isMkdir) {
                    log.error("Unable to create video directory: {}", fullDirectory);
                    CommonErrorBody errorBody = new CommonErrorBody(transactionId.toString(), "Unable to create video directory");
                    return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            for (MultipartFile multipartFile : fileArray) {
                final String fileName = multipartFile.getOriginalFilename();

                // RestClient: Save video information
                ResponseEntity<?> clientResponse = videoCatalogRestClientService.saveVideoInfo(userId, productId, fileName, "");

                if (clientResponse.getStatusCode() == HttpStatus.CREATED) {
                    // Save file to designated directory
                    boolean result = saveVideoFileToDirectory(
                            fullDirectory + File.separator + fileName,
                            multipartFile);
                    if (!result) {
                        log.error("Fail to save video video file: {}", fullDirectory + File.separator + fileName);
                    }
                } else {
                    log.error("Fail to save video catalog: {}", clientResponse.getStatusCode());
                }
            }

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * SAVE VIDEO FILE
     *
     * @param videoPath
     * @param multipartFile
     * @implNote Save a video file to a designated directory
     */
    protected boolean saveVideoFileToDirectory(String videoPath, MultipartFile multipartFile) {
        try {
            final byte[] fileData = multipartFile.getBytes();
            File file = new File(videoPath);
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(fileData);
            outputStream.close();
            return true;
        } catch (IOException ioe) {
            log.error("Error saving file: {}", multipartFile.getOriginalFilename());
            return false;
        }
    }


    /**
     * DELETE SELECTED VIDEO FILE
     *
     * @param videoPathList
     * @implNote Delete selected video file(s) for a product
     */
    @Override
    public ResponseEntity<?> deleteSelectedVideo(List<String> videoPathList) {
        try {
            for (String videoPath : videoPathList) {
                final boolean isDeleted = deleteVideoFileFromPath(videoPath);
                if (!isDeleted) {
                    log.error("Unable to delete video file: {}", videoPath);
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DELETE SELECTED VIDEO FILES
     *
     * @param path
     * @implNote Delete a video file from a path
     */
    protected boolean deleteVideoFileFromPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            log.warn("{} deleted: {}", path, isDeleted);
            return true;
        } else {
            log.warn("File does not exist: {}", path);
            return false;
        }
    }


    /**
     * DELETE ALL VIDEO FILES
     *
     * @param userId
     * @param productId
     * @apiNote Delete all videos for a product. From product deletion.
     */
    public ResponseEntity<?> deleteAllVideos(String userId, String productId) {
        try {
            final String fullDirectory = VIDEO_BASE_DIR + userId + File.separator + productId;
            File directory = new File(fullDirectory);
            if (!directory.exists()) {
                boolean isMkdir = directory.delete();
                if (!isMkdir) {
                    log.error("Unable to create video directory: {}", fullDirectory);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
