package com.yhdc.video_stream_server.object;

import lombok.Data;

@Data
public class VideoInfoDto {

    private String title;
    private String contentType;
    private String fileSize;
    private String filePath;

}
