package com.yhdc.video_catalog_server;

import org.springframework.boot.SpringApplication;

public class TestVideoCatalogServerApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductServerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
