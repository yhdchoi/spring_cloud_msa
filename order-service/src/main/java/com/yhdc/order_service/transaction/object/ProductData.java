package com.yhdc.order_service.transaction.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductData {

    private String productId;
    private String skuCode;
    private String name;
    private String price;
    private String quantity;
    private boolean isStock;

}
