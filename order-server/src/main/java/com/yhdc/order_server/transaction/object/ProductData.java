package com.yhdc.order_server.transaction.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductData {

    private String productId;
    private String name;
    private String price;

    private String quantity;
    private boolean isStock;

}
