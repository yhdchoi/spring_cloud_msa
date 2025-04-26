package com.yhdc.inventory_service.transaction;

import com.yhdc.inventory_service.transaction.object.InventoryCommonRecord;
import com.yhdc.inventory_service.transaction.object.InventoryCreateRecord;
import org.springframework.http.ResponseEntity;

public interface InventoryService {

    ResponseEntity<?> createInventory(InventoryCreateRecord inventoryCreateRecord);

    String checkInventoryStock(String productId, String skuCode, String quantity);

    String getInventoryStock(String productId, String skuCode);

    ResponseEntity<?> increaseInventoryStock(InventoryCommonRecord inventoryCommonRecord);

    ResponseEntity<?> decreaseInventoryStock(InventoryCommonRecord inventoryCommonRecord);

    ResponseEntity<?> updateSku(String productId, String skuCode, String newSkuCode);

    ResponseEntity<?> deleteInventory(String productId, String skuCode);
}
