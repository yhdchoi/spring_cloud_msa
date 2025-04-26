package com.yhdc.product_service.transaction.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageInfoListDto {
    private String productId;
    private List<ImageInfoDto> imageInfoDtoList;
}
