package com.yhdc.file_server;

import org.springframework.boot.SpringApplication;

public class TestFileServerApplication {

	public static void main(String[] args) {
		SpringApplication.from(FileServerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
