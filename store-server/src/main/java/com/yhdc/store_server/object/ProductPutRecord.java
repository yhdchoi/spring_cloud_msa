package com.yhdc.store_server.object;

import java.util.Objects;

public record ProductPutRecord(String productId,
                               String name,
                               String description,
                               String price,
                               String status,
                               String stock) {

    public ProductPutRecord {
        Objects.requireNonNull(productId, "User ID must not be null");
        Objects.requireNonNull(name, "Store ID must not be null");
        Objects.requireNonNull(description, "Status must not be null");
        Objects.requireNonNull(price, "Status must not be null");
        Objects.requireNonNull(status, "Status must not be null");
        Objects.requireNonNull(stock, "Status must not be null");
    }

}
