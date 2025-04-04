package com.yhdc.video_catalog_server;

import com.yhdc.video_catalog_server.data.VideoInfo;
import com.yhdc.video_catalog_server.object.VideoInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class VideoInfoController {

    private final VideoInfoServiceImpl videoInfoService;

    @PostMapping("/video-info/save")
    public List<VideoInfo> saveVideoInfo(@RequestBody List<VideoInfoDto> videoInfoDtoList) {
        return videoInfoService.createVideoInfo(videoInfoDtoList);
    }

    @GetMapping("/video-info/list")
    public List<VideoInfo> getMovieInfoList() {
        return videoInfoService.getVideoInfoList();
    }

    @GetMapping("/video-info/find-path")
    public String findVideoInfoByVideoInfoId(@RequestParam(name = "videoInfoId") String videoInfoId) {
        return videoInfoService.getVideoPathByVideoInfoId(videoInfoId);
    }

}
