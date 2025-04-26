package com.yhdc.product_service.transaction.object;

import java.util.Objects;

public record ProductCreateRecord(String userId,
                                  String storeId,
                                  String name,
                                  String description,
                                  String price,
                                  String quantity,
                                  String status,
                                  String skuCode) {

    public ProductCreateRecord {
        Objects.requireNonNull(userId, "User ID must not be null");
        Objects.requireNonNull(storeId, "Store ID must not be null");
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(description, "Description must not be null");
        Objects.requireNonNull(price, "Price must not be null");
        Objects.requireNonNull(status, "Status must not be null");
        Objects.requireNonNull(skuCode, "SKU Code must not be null");
        Objects.requireNonNull(quantity, "Quantity must not be null");
    }

}
