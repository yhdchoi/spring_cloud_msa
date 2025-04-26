package com.yhdc.store_service;

import org.springframework.boot.SpringApplication;

public class TestStoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(StoreServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
