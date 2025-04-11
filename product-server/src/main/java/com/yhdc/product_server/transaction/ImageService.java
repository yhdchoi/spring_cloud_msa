package com.yhdc.product_server.transaction;

import com.yhdc.product_server.object.ImageInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yhdc.product_server.type.Constants.IMAGE_BASE_DIR;
import static java.lang.System.out;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final FileService fileService;
    /**
     * SAVE IMAGE TO A DIRECTORY
     *
     * @param dirId
     * @param fileArray
     * @implNote
     * @implSpec
     */
    public ResponseEntity<?> saveImages(String dirId, MultipartFile[] fileArray) {
        // Save product images
        if (fileArray != null) {
            boolean directoryExists = false;
            final String imageDirectory = IMAGE_BASE_DIR + dirId;
            if (new File(imageDirectory).exists()) {
                directoryExists = true;
                log.warn("Image directory already exists: {}", imageDirectory);
            } else {
                // Create new directory if doesn't exists
                directoryExists = new File(imageDirectory).mkdirs();
                log.info("Image directory created: {}", imageDirectory);
            }

            if (directoryExists) {
                for (MultipartFile newFile : fileArray) {
                    fileService.saveFileToDir(newFile, imageDirectory);
                }
                log.info("Image(s) saved to: {}", imageDirectory);
                return new ResponseEntity<>(HttpStatus.OK);

            } else {
                log.warn("Image directory does not exist: {}", imageDirectory);
                return new ResponseEntity<>("Unable to create image directory.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("File array is empty!!!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * STREAM A PRODUCT IMAGE
     *
     * @param dirId
     * @param imageName
     * @implNote
     * @implSpec
     */
    public StreamingResponseBody streamImage(String dirId, String imageName) {

        try {
            final String imagePath = IMAGE_BASE_DIR + dirId + "/" + imageName;

            return outputStream -> {
                try {
                    File fi = new File(imagePath);
                    byte[] fileContent = Files.readAllBytes(fi.toPath());
                    int offset = 0,
                            chunkLength = 10000,
                            fileContentLength = fileContent.length;

                    while (offset + chunkLength < fileContentLength) {
                        out.write(fileContent, offset, chunkLength);
                        out.flush();
                        offset = offset + chunkLength;

                        if (fileContentLength < offset + chunkLength) {
                            chunkLength = fileContentLength - offset;
                        }

                        //Deliberately adding sleep to emulate buffering image
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    out.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * LOAD ALL IMAGES FROM A PRODUCT DIRECTORY
     *
     * @param dirId
     * @implNote Loads file name with url for download
     */
    public ResponseEntity<List<ImageInfoDto>> loadAllImages(String dirId) {
        try {
            final String imagePath = IMAGE_BASE_DIR + dirId;
            Path root = Paths.get(imagePath);
            Stream<Path> pathStream = Files.walk(root, 1).filter(path -> !path.equals(root)).map(root::relativize);

            List<ImageInfoDto> fileInfos = pathStream.map(path -> {

                String filename = path.getFileName().toString();
                // Extract URL from response
                String url = MvcUriComponentsBuilder
                        .fromMethodName(ImageService.class, "loadImage",
                                dirId, filename).build().toString();
                return new ImageInfoDto(filename, url);

            }).collect(Collectors.toList());

            return new ResponseEntity<>(fileInfos, HttpStatus.OK);

        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * LOAD FILE FROM A DIRECTORY
     *
     * @param dirId
     * @param filename
     * @implNote
     */
    public ResponseEntity<Resource> loadImage(String dirId, String filename) {
        try {
            final String imagePath = IMAGE_BASE_DIR + dirId;
            Path root = Paths.get(imagePath);
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    /**
     * DOWNLOAD PRODUCT IMAGES ZIP
     *
     * @param dirId
     * @implNote Zip the main image directory along with the subdirectories if exists
     * @implSpec
     */
    public ResponseEntity<Resource> downloadImageDirZip(String dirId) {

        try {
            final String productImageDir = IMAGE_BASE_DIR + dirId;
            File imageDirectory = new File(productImageDir);
            if (!imageDirectory.exists() && imageDirectory.listFiles() != null) {
                final String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                final String zippedFileDir = "/stackcore/tmp";
                final String zipFileName = dirId + "_dirCompressed_" + dateTime + ".zip";

                Resource resource = fileService.createZipFile(productImageDir, zippedFileDir, zipFileName);
                return new ResponseEntity<>(resource, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * PATCHES PRODUCT IMAGES
     *
     * @param dirId
     * @param fileArray
     * @implNote Saves new image(s)
     * @implSpec
     */
    public ResponseEntity<?> patchImage(String dirId,
                                        MultipartFile[] fileArray) {

        final String productImageDir = IMAGE_BASE_DIR + dirId;
        // Save new images
        if (fileArray != null) {
            for (MultipartFile file : fileArray) {
                fileService.saveFileToDir(file, productImageDir);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * DELETE PRODUCT IMAGE(S)
     *
     * @param dirId
     * @param deletedFileNameList
     */
    public ResponseEntity<?> deleteImageDir(String dirId,
                                            List<String> deletedFileNameList) {

        final String productImageDir = IMAGE_BASE_DIR + dirId;
        if (deletedFileNameList != null && !deletedFileNameList.isEmpty()) {
            for (String fileName : deletedFileNameList) {
                fileService.deleteFileFromDir(productImageDir, fileName);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
