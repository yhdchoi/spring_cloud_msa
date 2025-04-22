package com.yhdc.video_stream_server.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoCatalogRestClientService {

    private static final String videoCatalogServerUrl = "http://localhost:8101/video-info";

    private final RestClient restClient;


    /**
     * GET VIDEO PATH
     *
     * @param videoInfoId
     * @implNote For streaming video
     */
    public String loadVideoPath(String videoInfoId) {
        return restClient.get()
                .uri(videoCatalogServerUrl + "/find-path/{videoInfoId}", videoInfoId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
    }


    /**
     * SAVE VIDEO INFORMATION
     *
     * @param fileName
     * @param description
     */
    public ResponseEntity<?> saveVideoInfo(String userId, String productId, String fileName, String description) {

        VideoInfoSaveRecord videoInfoSaveRecord = new VideoInfoSaveRecord(userId, productId, fileName, description);

        return restClient.post()
                .uri(videoCatalogServerUrl + "/save")
                .body(videoInfoSaveRecord)
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
