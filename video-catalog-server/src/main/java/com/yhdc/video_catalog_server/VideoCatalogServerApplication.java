package com.yhdc.video_catalog_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class VideoCatalogServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoCatalogServerApplication.class, args);
	}

}
