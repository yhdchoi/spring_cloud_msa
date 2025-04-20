package com.yhdc.order_server.transaction.object;

import lombok.Data;

import java.util.List;

@Data
public class StoreData {

    private String storeId;
    private String name;
    private List<ProductData> productDataList;

}
