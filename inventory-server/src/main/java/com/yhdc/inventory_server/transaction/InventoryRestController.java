package com.yhdc.inventory_server.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class InventoryRestController {

    private final InventoryServiceImpl inventoryService;

    @GetMapping("/detail")
    public ResponseEntity<?> getInventoryDetail(@RequestParam(value = "inventoryId") String inventoryId) {
        return inventoryService.detailInventory(inventoryId);
    }

    @PostMapping("/increase")
    public ResponseEntity<?> increaseQuantity(@RequestBody InventoryCommonRecord inventoryCommonRecord) {
        return inventoryService.increaseQuantity(inventoryCommonRecord);
    }

    @PatchMapping("/patch/sku")
    public ResponseEntity<?> patchSku(@RequestParam String productId,
                                      @RequestParam String skuCode,
                                      @RequestParam String newSkuCode) {
        return inventoryService.updateSku(productId, skuCode, newSkuCode);
    }

    @GetMapping("/stock")
    public boolean getInventoryDetail(@RequestParam String productId,
                                      @RequestParam String skuCode,
                                      @RequestParam String quantity) {
        return inventoryService.checkStock(productId, skuCode, quantity);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createInventory(InventoryCreateRecord inventoryCreateRecord) {
        return inventoryService.createNewInventory(inventoryCreateRecord);
    }

    @PatchMapping("/decrease")
    public ResponseEntity<?> decreaseInventory(InventoryCommonRecord inventoryCommonRecord) {
        return inventoryService.decreaseQuantity(inventoryCommonRecord);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInventory(String productId, String skuCode) {
        return inventoryService.deleteInventory(productId, skuCode);
    }


}
