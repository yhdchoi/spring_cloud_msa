package com.yhdc.video_stream_server.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VideoStreamController {

    private final VideoStreamServiceImpl videoStreamService;
    private final VideoCatalogRestClient videoCatalogRestClient;


    /**
     * STREAM VIDEO BY PATH
     *
     * @param videoPath
     * @throws IOException
     */
    @GetMapping("/video-stream/video")
    public ResponseEntity<StreamingResponseBody> streamVideoRsp(@RequestParam String videoPath) {
        return videoStreamService.getVideoStream(videoPath);
    }


    /**
     * STREAM VIDEO BY VIDEO INFO ID
     *
     * @param videoInfoId
     * @apiNote Rest client request
     */
    @GetMapping("/video-stream/videoInfoId")
    public ResponseEntity<StreamingResponseBody> streamVideoInfo(@RequestParam String videoInfoId) {
        final String videoPath = videoCatalogRestClient.loadVideoPath(videoInfoId);
        return videoStreamService.getVideoStream(videoPath);
    }

}
