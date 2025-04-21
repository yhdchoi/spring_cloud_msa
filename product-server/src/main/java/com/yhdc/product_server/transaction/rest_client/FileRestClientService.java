package com.yhdc.product_server.transaction.rest_client;

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
public class FileRestClientService {

    private static final String fileServerUrl = "http://localhost:8100/file";

    private final RestClient restClient;

    /**
     * LOAD PRODUCT IMAGES' INFORMATION
     *
     * @param productId
     */
    public ResponseEntity<?> loadAllProductImageInfo(String productId) {

        return restClient.get()
                .uri(fileServerUrl + "/load/image-info/{dirId}", productId)
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
     * DELETE ALL PRODUCT IMAGES
     *
     * @param productId
     */
    public ResponseEntity<?> deleteProductImages(String productId) {

        log.info("Deleting images for Product id {}", productId);

        final ResponseEntity<?> clientResponse
                = restClient.delete()
                .uri(fileServerUrl + "/delete/image-dir/{dirId}", productId)
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

        log.info("Response received: {}", clientResponse);
        return clientResponse;
    }

}
