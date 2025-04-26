package com.yhdc.video_stream_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class VideoStreamServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoStreamServerApplication.class, args);
	}

}
