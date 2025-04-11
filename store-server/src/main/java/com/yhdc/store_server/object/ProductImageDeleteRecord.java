package com.yhdc.store_server.object;


import java.util.List;
import java.util.Objects;

public record ProductImageDeleteRecord(String productId,
                                       List<String> fileNameList) {

    public ProductImageDeleteRecord {
        Objects.requireNonNull(productId, "Product ID must not be null");
        Objects.requireNonNull(fileNameList, "File name list must not be null");
    }

}
