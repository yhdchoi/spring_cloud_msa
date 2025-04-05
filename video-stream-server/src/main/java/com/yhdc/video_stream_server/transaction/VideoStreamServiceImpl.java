package com.yhdc.video_stream_server.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class VideoStreamServiceImpl {


    public InputStreamResource getVideoStreamDto(String videoPath) {
        try {
            Path path = Paths.get(videoPath);
            log.info("Video path: {}", path.toString());
            File file = new File(path.toFile().getAbsolutePath());
            log.info("File path: {}", file.getAbsolutePath());
            if (!file.exists()) {
                return new InputStreamResource(new FileInputStream(file));
            } else {
                throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
