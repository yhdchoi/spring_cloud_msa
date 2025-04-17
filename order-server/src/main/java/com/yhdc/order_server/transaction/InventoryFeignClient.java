package com.yhdc.order_server.transaction;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "inventory", url = "http://localhost:8085")
@Component
public interface InventoryFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/inventory/stock")
    boolean isInStock(@RequestParam String productId, @RequestParam int quantity);

}
