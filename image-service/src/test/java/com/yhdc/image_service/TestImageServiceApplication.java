package com.yhdc.image_service;

import org.springframework.boot.SpringApplication;

public class TestImageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ImageServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
