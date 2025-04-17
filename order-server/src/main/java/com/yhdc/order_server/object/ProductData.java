package com.yhdc.order_server.object;

import lombok.Data;

@Data
public class ProductData {

    private String productId;
    private String name;
    private String price;

    private String count;

}
