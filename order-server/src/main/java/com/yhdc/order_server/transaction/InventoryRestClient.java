package com.yhdc.order_server.transaction;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
@Component
public interface InventoryRestClient {

    @GetExchange("/stock")
    boolean isInStock(@RequestParam String productId, @RequestParam String quantity);

}
