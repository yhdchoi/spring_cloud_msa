package com.yhdc.video_stream_server;

import org.springframework.boot.SpringApplication;

public class TestVideoStreamServerApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductServerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
