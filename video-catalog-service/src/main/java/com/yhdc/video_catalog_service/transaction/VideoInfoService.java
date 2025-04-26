package com.yhdc.video_catalog_service.transaction;

import com.yhdc.video_catalog_service.transaction.data.VideoInfo;
import com.yhdc.video_catalog_service.transaction.object.VideoInfoSaveRecord;
import com.yhdc.video_catalog_service.transaction.object.VideoInfoUpdateRecord;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VideoInfoService {

    ResponseEntity<?> createVideoInfo(VideoInfoSaveRecord videoInfoSaveRecord);

    List<VideoInfo> getVideoInfoList();

    String getVideoPathByVideoInfoId(String videoInfoId);

    ResponseEntity<?> updateVideoInfo(VideoInfoUpdateRecord videoInfoUpdateRecord);

    ResponseEntity<?> deleteSelectedVideoInfo(List<String> videoInfoIdList);

    ResponseEntity<?> deleteProductVideoInfo(String userId, String productId);

}
