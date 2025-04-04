package com.yhdc.video_catalog_server;

import com.yhdc.video_catalog_server.data.VideoInfo;

import java.util.List;

public interface VideoInfoService {

    List<VideoInfo> createVideoInfo(List<VideoInfo> videoInfoList);

    List<VideoInfo> getVideoInfoList();

    String getVideoPathByVideoInfoId(String videoInfoId);
}
