package com.yhdc.inventory_server.transaction;

import com.yhdc.inventory_server.transaction.object.InventoryCommonRecord;
import com.yhdc.inventory_server.transaction.object.InventoryCreateRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class InventoryApiController {

    private final InventoryServiceImpl inventoryService;


    @GetMapping("/stock-check")
    public String getInventoryDetail(@RequestParam String productId,
                                      @RequestParam String skuCode,
                                      @RequestParam String quantity) {
        return inventoryService.checkInventoryStock(productId, skuCode, quantity);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createInventory(@RequestBody InventoryCreateRecord inventoryCreateRecord) {
        return inventoryService.createInventory(inventoryCreateRecord);
    }

    @GetMapping("/stock")
    public String getInventoryStock(@RequestParam(value = "productId") String productId,
                                     @RequestParam(value = "skuCode") String skuCode) {
        return inventoryService.getInventoryStock(productId, skuCode);
    }

    @PatchMapping("/increase")
    public ResponseEntity<?> increaseQuantity(@RequestBody InventoryCommonRecord inventoryCommonRecord) {
        return inventoryService.increaseInventoryStock(inventoryCommonRecord);
    }

    @PatchMapping("/decrease")
    public ResponseEntity<?> decreaseInventory(@RequestBody InventoryCommonRecord inventoryCommonRecord) {
        return inventoryService.decreaseInventoryStock(inventoryCommonRecord);
    }

    @PatchMapping("/patch/sku")
    public ResponseEntity<?> patchSku(@RequestParam(value = "productId") String productId,
                                      @RequestParam(value = "skuCode") String skuCode,
                                      @RequestParam(value = "newSkuCode") String newSkuCode) {
        return inventoryService.updateSku(productId, skuCode, newSkuCode);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInventory(@RequestParam(value = "productId") String productId,
                                             @RequestParam(value = "skuCode") String skuCode) {
        return inventoryService.deleteInventory(productId, skuCode);
    }

}
