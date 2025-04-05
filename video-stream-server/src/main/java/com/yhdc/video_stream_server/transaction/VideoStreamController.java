package com.yhdc.video_stream_server.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VideoStreamController {

    private final VideoStreamServiceImpl videoStreamService;

    @GetMapping("/video-stream/video")
    public ResponseEntity<InputStreamResource> streamVideo(@RequestParam String filePath) {
        InputStreamResource responseStream = videoStreamService.getVideoStreamDto(filePath);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("video/mp4")).body(responseStream);
    }


}
