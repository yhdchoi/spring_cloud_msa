package com.yhdc.order_server.transaction;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "inventory", url = "http://localhost:8085")
public interface InventoryFeignClient {

    @GetMapping("/inventory/stock")
    boolean isInStock(@RequestParam String productId, @RequestParam String quantity);

}
