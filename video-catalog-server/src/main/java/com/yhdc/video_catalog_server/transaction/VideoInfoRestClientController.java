package com.yhdc.video_catalog_server.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class VideoInfoRestClientController {

    private final VideoInfoServiceImpl videoInfoService;

    /**
     * DELETE VIDEO INFO
     *
     * @param videoInfoId
     * @apiNote Delete video information with video file
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVideoInfo(@RequestParam(name = "videoInfoId") String videoInfoId) {
        return videoInfoService.deleteVideoInfo(videoInfoId);
    }

}
