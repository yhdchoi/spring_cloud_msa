package com.yhdc.store_server.transaction.object;

import lombok.Data;

@Data
public class StoreDto {

    private String storeId;
    private String userId;
    private String name;
    private String description;
    private String status;

}
