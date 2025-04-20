package com.yhdc.product_server.transaction;

import com.yhdc.product_server.transaction.object.InventoryCommonRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "inventory", url = "http://localhost:8085")
public interface InventoryFeignClient {

    @GetMapping("/inventory/stock")
    boolean isInStock(@RequestParam String productId,
                      @RequestParam String skuCode,
                      @RequestParam String quantity);

    @PatchMapping("/inventory/create")
    ResponseEntity<?> createStock(@RequestBody InventoryCommonRecord inventoryCommonRecord);

    @GetMapping("/inventory/stock")
    String getInventoryStock(@RequestParam String productId, @RequestParam String skuCode);

    @PatchMapping("/inventory/increase")
    ResponseEntity<?> increaseStock(@RequestBody InventoryCommonRecord inventoryCommonRecord);

    @PatchMapping("/inventory/decrease")
    ResponseEntity<?> decreaseStock(@RequestBody InventoryCommonRecord inventoryCommonRecord);

    @PatchMapping("/inventory/patch/sku")
    ResponseEntity<?> patchSku(@RequestParam String productId,
                               @RequestParam String skuCode,
                               @RequestParam String newSkuCode);

    @DeleteMapping("/inventory/delete")
    ResponseEntity<?> deleteStock(@RequestParam String productId,
                                  @RequestParam String skuCode);
}
