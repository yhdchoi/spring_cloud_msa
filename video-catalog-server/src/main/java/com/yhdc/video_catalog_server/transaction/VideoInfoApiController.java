package com.yhdc.video_catalog_server.transaction;

import com.yhdc.video_catalog_server.transaction.data.VideoInfo;
import com.yhdc.video_catalog_server.transaction.object.VideoInfoSaveRecord;
import com.yhdc.video_catalog_server.transaction.object.VideoInfoUpdateRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class VideoInfoApiController {

    private final VideoInfoServiceImpl videoInfoService;


    /**
     * LIST ALL VIDEO INFORMATION
     *
     * @apiNote
     */
    @GetMapping("/list")
    public List<VideoInfo> getMovieInfoList() {
        return videoInfoService.getVideoInfoList();
    }


    /**
     * LOAD VIDEO PATH BY VIDEO INFO ID
     *
     * @param videoInfoId
     * @apiNote Used by the video stream server
     */
    @GetMapping("/find-path")
    public String findVideoInfoByVideoInfoId(@RequestParam(name = "videoInfoId") String videoInfoId) {
        return videoInfoService.getVideoPathByVideoInfoId(videoInfoId);
    }


    /**
     * UPDATE VIDEO INFORMATION
     *
     * @param videoInfoUpdateRecord
     * @apiNote
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateVideoInfo(@RequestBody VideoInfoUpdateRecord videoInfoUpdateRecord) {
        return videoInfoService.updateVideoInfo(videoInfoUpdateRecord);
    }

}
