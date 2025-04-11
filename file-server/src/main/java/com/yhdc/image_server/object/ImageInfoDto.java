package com.yhdc.image_server.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageInfoDto {
    private String filename;
    private String url;
}
