package com.yhdc.video_catalog_server.transaction;

import com.yhdc.video_catalog_server.transaction.data.VideoInfo;
import com.yhdc.video_catalog_server.transaction.object.VideoInfoSaveRecord;
import com.yhdc.video_catalog_server.transaction.object.VideoInfoUpdateRecord;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VideoInfoService {

    ResponseEntity<?> createVideoInfo(VideoInfoSaveRecord videoInfoSaveRecord);

    List<VideoInfo> getVideoInfoList();

    String getVideoPathByVideoInfoId(String videoInfoId);

    ResponseEntity<?> updateVideoInfo(VideoInfoUpdateRecord videoInfoUpdateRecord);

    ResponseEntity<?> deleteSelectedVideoInfo(String userId, List<String> videoInfoIdList);

    ResponseEntity<?> deleteProductVideoInfo(String userId, String productId);

}
