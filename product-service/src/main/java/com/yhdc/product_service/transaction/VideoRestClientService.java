package com.yhdc.product_service.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoRestClientService {

    private static final String videoCatalogServerUrl = "http://localhost:8101/video-catalog";

    private final RestClient restClient;


    /**
     * DELETE VIDEO CATALOG
     *
     * @param productId
     */
    public ResponseEntity<?> deleteVideo(String productId, String sellerId) {

        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        params.put("userId", sellerId);

        return restClient.delete()
                .uri(videoCatalogServerUrl + "/delete", params)
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
