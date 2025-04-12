package com.yhdc.product_server.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveImageDto {
    private String dirId;
    private MultipartFile[] fileArray;
}
