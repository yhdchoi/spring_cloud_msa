package com.yhdc.inventory_server.transaction;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "product", url = "http://localhost:8083")
public interface ProductFeignClient {


}
