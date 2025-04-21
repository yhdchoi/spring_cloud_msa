package com.yhdc.product_server;

import org.springframework.boot.SpringApplication;

public class TestProductServerApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductServerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
