package com.yhdc.video_catalog_server.transaction;

import com.yhdc.video_catalog_server.data.VideoInfo;
import com.yhdc.video_catalog_server.object.VideoInfoSaveRecord;
import com.yhdc.video_catalog_server.object.VideoInfoUpdateRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class VideoInfoController {

    private final VideoInfoServiceImpl videoInfoService;

    @PostMapping("/video-info/save")
    public List<VideoInfo> saveVideoInfo(@RequestBody List<VideoInfoSaveRecord> videoInfoSaveRecordList) {
        return videoInfoService.createVideoInfo(videoInfoSaveRecordList);
    }

    @GetMapping("/video-info/list")
    public List<VideoInfo> getMovieInfoList() {
        return videoInfoService.getVideoInfoList();
    }


    /**
     * LOAD VIDEO PATH BY VIDEO INFO ID
     *
     * @param videoInfoId
     * @apiNote Used by the video stream server
     */
    @GetMapping("/video-info/find-path")
    public String findVideoInfoByVideoInfoId(@RequestParam(name = "videoInfoId") String videoInfoId) {
        return videoInfoService.getVideoPathByVideoInfoId(videoInfoId);
    }

    @PutMapping("/video-info/update")
    public ResponseEntity<?> updateVideoInfo(@RequestBody VideoInfoUpdateRecord videoInfoUpdateRecord) {
        return videoInfoService.updateVideoInfo(videoInfoUpdateRecord);
    }

}
