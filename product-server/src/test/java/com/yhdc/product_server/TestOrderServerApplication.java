package com.yhdc.product_server;

import org.springframework.boot.SpringApplication;

public class TestOrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductServerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
