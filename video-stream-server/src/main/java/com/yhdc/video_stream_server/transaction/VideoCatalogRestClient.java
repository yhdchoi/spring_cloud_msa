package com.yhdc.video_stream_server.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.yhdc.video_stream_server.transaction.Constants.VIDEO_CATALOG_SERVER_URI;

@Slf4j
@RequiredArgsConstructor
@Component
public class VideoCatalogRestClient {

    private final RestTemplate restTemplate;


    public String loadVideoPath(String videoInfoId) {

        URI uri = UriComponentsBuilder
                .fromUriString(VIDEO_CATALOG_SERVER_URI)
                .path("/video-info/find-path")
                .queryParam("videoInfoId", videoInfoId)
                .encode()
                .build()
                .toUri();

        log.info("load video info id {}", videoInfoId);
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        log.info("Video path received: {}", response);
        return response.getBody();
    }

}
