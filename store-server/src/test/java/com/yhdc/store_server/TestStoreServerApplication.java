package com.yhdc.store_server;

import org.springframework.boot.SpringApplication;

public class TestStoreServerApplication {

    public static void main(String[] args) {
        SpringApplication.from(StoreServerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
