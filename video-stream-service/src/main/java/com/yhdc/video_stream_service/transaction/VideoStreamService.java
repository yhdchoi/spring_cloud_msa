package com.yhdc.video_stream_service.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

public interface VideoStreamService {

    ResponseEntity<StreamingResponseBody> getVideoStream(String videoPath);

    ResponseEntity<?> saveProductVideos(String userId, String productId, MultipartFile[] fileArray);

    ResponseEntity<?> deleteSelectedVideo(List<String> videoPathList);

}
