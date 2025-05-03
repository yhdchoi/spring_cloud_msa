package com.yhdc.store_service.transaction.object;

import lombok.Data;

@Data
public class StoreDto {

    private String storeId;
    private String sellerId;
    private String name;
    private String description;
    private String status;

}
