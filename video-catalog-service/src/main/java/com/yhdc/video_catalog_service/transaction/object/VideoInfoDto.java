package com.yhdc.video_catalog_service.transaction.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VideoInfoDto {

    private String userId;
    private String productId;
    private String fileName;
    private String description;
    private String videoPath;

}
