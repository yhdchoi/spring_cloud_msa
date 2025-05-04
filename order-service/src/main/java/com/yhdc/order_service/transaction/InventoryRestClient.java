package com.yhdc.order_service.transaction;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
@Component
public interface InventoryRestClient {

    /**
     * CHECK STOCK FOR ORDER QUANTITY
     *
     * @param productId
     * @param skuCode
     * @param quantity
     * @implNote For checking product stock if it is ok to process with the order
     * @implSpec RestClient is configured in RestClientConfig. Timeout is set in to RestClient configuration.
     */
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    @GetExchange("/stock")
    String isInStock(@RequestParam String productId,
                     @RequestParam String skuCode,
                     @RequestParam String quantity);

    default boolean fallbackMethod() {
        return false;
    }

}
