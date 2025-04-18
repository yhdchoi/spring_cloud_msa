package com.yhdc.inventory_server;

import org.springframework.boot.SpringApplication;

public class TestInventoryServerApplication {

	public static void main(String[] args) {
		SpringApplication.from(InventoryServerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
