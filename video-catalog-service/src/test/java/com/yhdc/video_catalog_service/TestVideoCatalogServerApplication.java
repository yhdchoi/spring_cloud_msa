package com.yhdc.video_catalog_service;

import org.springframework.boot.SpringApplication;

public class TestVideoCatalogServerApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductServerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
