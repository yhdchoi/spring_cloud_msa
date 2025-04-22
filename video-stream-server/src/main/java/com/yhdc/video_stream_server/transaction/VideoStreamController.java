package com.yhdc.video_stream_server.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VideoStreamController {

    private final VideoStreamServiceImpl videoStreamService;
    private final VideoCatalogRestClientService videoCatalogRestClientService;

    /**
     * STREAM VIDEO BY VIDEO INFO ID
     *
     * @param videoInfoId
     * @apiNote Stream video. RestClient request for video path.
     */
    @GetMapping("/stream/id")
    public ResponseEntity<StreamingResponseBody> streamVideoInfo(@RequestParam String videoInfoId) {
        final String videoPath = videoCatalogRestClientService.loadVideoPath(videoInfoId);
        return videoStreamService.getVideoStream(videoPath);
    }

//    @GetMapping("/video-stream/stream/path")
//    public ResponseEntity<StreamingResponseBody> streamVideoInfo(@RequestParam String videoPath) {
//        final String videoPath = videoCatalogRestClientService.loadVideoPath(videoInfoId);
//        return videoStreamService.getVideoStream(videoPath);
//    }


    /**
     * SAVE VIDEOS
     *
     * @param productId
     * @param fileArray
     * @apiNote Save video(s) for a product. RestClient request for catalog.
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveVideo(@RequestParam String userId, @RequestParam String productId, @RequestPart MultipartFile[] fileArray) {
        return videoStreamService.saveProductVideos(userId, productId, fileArray);
    }


    /**
     * DELETE SELECTED VIDEOS
     *
     * @param videoPathList
     * @apiNote RestClient api
     */
    @DeleteMapping("/delete/selected-video")
    public ResponseEntity<?> deleteSelectedVideo(@RequestParam List<String> videoPathList) {
        return videoStreamService.deleteSelectedVideo(videoPathList);
    }


    /**
     * DELETE ALL PRODUCT VIDEOS
     *
     * @param userId
     * @param productId
     * @apiNote RestClient api
     */
    @DeleteMapping("/delete/product-video")
    public ResponseEntity<?> deleteProductVideo(@RequestParam String userId, @RequestParam String productId) {
        return videoStreamService.deleteAllVideos(userId, productId);
    }

}
