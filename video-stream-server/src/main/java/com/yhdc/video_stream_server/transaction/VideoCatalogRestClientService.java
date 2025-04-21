package com.yhdc.video_stream_server.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoCatalogRestClientService {

    private static final String videoCatalogServerUrl = "http://localhost:8101/video-info";

    private final RestClient restClient;

    public String loadVideoPath(String videoInfoId) {
        return restClient.get()
                .uri(videoCatalogServerUrl + "/find-path/{videoInfoId}", videoInfoId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
    }

}
