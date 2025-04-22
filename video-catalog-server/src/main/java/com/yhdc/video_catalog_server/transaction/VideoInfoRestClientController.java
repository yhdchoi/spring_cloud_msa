package com.yhdc.video_catalog_server.transaction;

import com.yhdc.video_catalog_server.transaction.data.VideoInfo;
import com.yhdc.video_catalog_server.transaction.object.VideoInfoSaveRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class VideoInfoRestClientController {

    private final VideoInfoServiceImpl videoInfoService;

    /**
     * CREATE VIDEO INFORMATION
     *
     * @param videoInfoSaveRecord
     * @apiNote Create a new video information for a video file
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveVideoInfo(@RequestBody VideoInfoSaveRecord videoInfoSaveRecord) {
        return videoInfoService.createVideoInfo(videoInfoSaveRecord);
    }

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
