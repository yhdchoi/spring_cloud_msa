package com.yhdc.video_stream_server.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface VideoStreamService {

    ResponseEntity<StreamingResponseBody> getVideoStream(String videoPath);

}
