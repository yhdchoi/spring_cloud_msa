package com.yhdc.video_stream_server.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoStreamServiceImpl {

    private final ResourceLoader resourceLoader;

    /**
     * LOAD VIDEO FILE THEN LOAD STREAM
     *
     * @param videoPath
     * @implNote
     */
    public ResponseEntity<StreamingResponseBody> getVideoStream(String videoPath) {
        try {
            Resource resource = resourceLoader.getResource( videoPath);
            File file = resource.getFile();
            if (!file.isFile()) {
                log.error("Video file not found path: {}", videoPath);
                return ResponseEntity.notFound().build();
            }

            StreamingResponseBody streamingResponseBody = new StreamingResponseBody() {
                @Override
                public void writeTo(OutputStream outputStream) {
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

}
