package com.yhdc.file_server.initializer;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.yhdc.file_server.type.Constants.FILE_BASE_DIR;

@Slf4j
@Component
public class DirectoryInitializer {


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void defaultProcessor() {

        File imageDir = new File(FILE_BASE_DIR);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
            log.warn("Created directory: {}", imageDir.getAbsolutePath());
        } else {
            log.info("Directory already exists: {}", imageDir.getAbsolutePath());
        }
        log.info("Exiting default processor");
    }

}
