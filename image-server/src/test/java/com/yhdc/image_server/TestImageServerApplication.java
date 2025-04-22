package com.yhdc.image_server;

import org.springframework.boot.SpringApplication;

public class TestImageServerApplication {

	public static void main(String[] args) {
		SpringApplication.from(ImageServerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
