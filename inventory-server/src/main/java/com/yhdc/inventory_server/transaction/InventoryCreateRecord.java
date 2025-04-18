package com.yhdc.inventory_server.transaction;

public record InventoryCreateRecord(String productId, String skuCode, String quantity) {

}
