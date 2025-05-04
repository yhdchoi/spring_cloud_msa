package com.yhdc.video_catalog_service.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoFileRestClientService {

    private static final String VideoServerUrl = "lb://VIDEO-STREAM-SERVICE/video-stream";

    private final RestClient restClient;


    /**
     * DELETE SELECTED VIDEOS
     *
     * @param videoPathList
     */
    public ResponseEntity<?> deleteSelectedVideoFiles(List<String> videoPathList) {
        return restClient.delete()
                .uri(VideoServerUrl + "/delete/selected-video/{videoPathList}", videoPathList)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
                    } else if (response.getStatusCode().is5xxServerError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
                    }
                });
    }


    /**
     * DELETE ALL PRODUCT VIDEOS
     *
     * @param userId
     * @param productId
     */
    public ResponseEntity<?> deleteAllProductVideoFiles(String userId, String productId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("productId", productId);

        return restClient.delete()
                .uri(VideoServerUrl + "/delete/product-video", params)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
                    } else if (response.getStatusCode().is5xxServerError()) {
                        // TODO: exception handling
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
                    }
                });
    }


}
