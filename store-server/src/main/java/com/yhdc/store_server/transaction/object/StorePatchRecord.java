package com.yhdc.store_server.transaction.object;

import java.util.Objects;

public record StorePatchRecord(String userId, String storeId, String status) {

    public StorePatchRecord {
        Objects.requireNonNull(userId, "User ID must not be null");
        Objects.requireNonNull(storeId, "Store ID must not be null");
        Objects.requireNonNull(status, "Status must not be null");
    }
}