package com.yhdc.inventory_server.transaction;

import com.yhdc.inventory_server.transaction.object.InventoryCommonRecord;
import com.yhdc.inventory_server.transaction.object.InventoryCreateRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class InventoryRestClientController {

    private final InventoryServiceImpl inventoryService;

    /**
     * CHECK INVENTORY STOCK
     *
     * @param productId
     * @param skuCode
     * @param quantity
     * @apiNote Check inventory stock for an order.
     */
    @GetMapping("/stock-check")
    public String checkInventory(@RequestParam String productId,
                                 @RequestParam String skuCode,
                                 @RequestParam String quantity) {
        return inventoryService.checkInventoryStock(productId, skuCode, quantity);
    }


    /**
     * GET INVENTORY QUANTITY
     *
     * @param productId
     * @param skuCode
     * @apiNote Get inventory quantity.
     */
    @GetMapping("/stock")
    public String getInventoryDetail(@RequestParam(value = "productId") String productId,
                                     @RequestParam(value = "skuCode") String skuCode) {
        return inventoryService.getInventoryStock(productId, skuCode);
    }


    /**
     * CREATE INVENTORY
     *
     * @param inventoryCreateRecord
     * @apiNote Create a new inventory for a new product.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createInventory(@RequestBody InventoryCreateRecord inventoryCreateRecord) {
        return inventoryService.createInventory(inventoryCreateRecord);
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
