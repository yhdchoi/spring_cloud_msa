package com.yhdc.order_service;

import org.springframework.boot.SpringApplication;

public class TestOrderServerApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrderServerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
