package com.yhdc.video_catalog_service.transaction;

import com.yhdc.video_catalog_service.transaction.object.VideoInfoSaveRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param videoInfoIdList
     * @apiNote Delete video information with video file
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVideoInfo(@RequestParam(name = "videoInfoIdList") List<String> videoInfoIdList) {
        return videoInfoService.deleteSelectedVideoInfo(videoInfoIdList);
    }

}
