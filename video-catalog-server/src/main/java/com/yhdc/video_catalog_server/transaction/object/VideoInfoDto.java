package com.yhdc.video_catalog_server.transaction.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VideoInfoDto {

    private String title;
    private String description;
    private String videoPath;

}
