package com.yhdc.store_server.object;

import java.util.Objects;

public record StorePutRecord(String id, String name) {

    public StorePutRecord {
        Objects.requireNonNull(id, "ID must not be null");
        Objects.requireNonNull(name, "Name must not be null");
    }
}