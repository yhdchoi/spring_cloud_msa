package com.yhdc.store_service.transaction.object;

import java.util.Objects;

public record StoreCreateRecord(String userId, String name, String description, String status) {

    public StoreCreateRecord {
        Objects.requireNonNull(userId, "User ID must not be null");
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(description, "Description must not be null");
        Objects.requireNonNull(status, "Status must not be null");
    }
}