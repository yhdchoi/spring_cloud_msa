package com.yhdc.video_catalog_server.transaction;

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
public class VideoFileRestClientService {

    private static final String fileServerUrl = "http://localhost:8100/file";

    private final RestClient restClient;


    /**
     * DELETE VIDEO FILE
     *
     * @param filePath
     */
    public ResponseEntity<?> deleteVideoFiles(String filePath) {
        return restClient.delete()
                .uri(fileServerUrl + "/video/delete/{path}", filePath)
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
