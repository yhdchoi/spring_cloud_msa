package com.yhdc.video_catalog_server.transaction;

import com.yhdc.video_catalog_server.data.VideoInfo;
import com.yhdc.video_catalog_server.object.VideoInfoSaveRecord;
import com.yhdc.video_catalog_server.object.VideoInfoUpdateRecord;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VideoInfoService {

    List<VideoInfo> createVideoInfo(List<VideoInfoSaveRecord> videoInfoSaveRecordList);

    List<VideoInfo> getVideoInfoList();

    String getVideoPathByVideoInfoId(String videoInfoId);

    ResponseEntity<?> updateVideoInfo(VideoInfoUpdateRecord videoInfoUpdateRecord);
}
