package com.yhdc.inventory_server.transaction.object;

public record InventoryCreateRecord(String productId, String skuCode, String quantity) {

}
