package com.yhdc.product_server.transaction.object;

import java.util.Objects;

public record ProductPutRecord(String productId,
                               String name,
                               String description,
                               String price,
                               String status,
                               String skuCode) {

    public ProductPutRecord {
        Objects.requireNonNull(productId, "User ID must not be null");
        Objects.requireNonNull(name, "Store ID must not be null");
        Objects.requireNonNull(description, "Status must not be null");
        Objects.requireNonNull(price, "Status must not be null");
        Objects.requireNonNull(status, "Status must not be null");
        Objects.requireNonNull(skuCode, "SKU Code must not be null");
    }

}
