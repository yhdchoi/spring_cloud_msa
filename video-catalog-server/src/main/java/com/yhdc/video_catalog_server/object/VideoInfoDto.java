package com.yhdc.video_catalog_server.object;

import lombok.Data;

@Data
public class VideoInfoDto {

    private final String title;
    private final String description;
    private final String filePath;

}
