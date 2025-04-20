package com.yhdc.product_server.transaction.object;

import lombok.Data;

@Data
public class ProductDto {

    private String productId;
    private String userId;
    private String storeId;
    private String name;
    private String description;
    private String price;
    private String status;
    private String skuCode;
    private String quantity;

    private String createdAt;
    private String modifiedAt;

    private String error;
}
