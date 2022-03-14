package com.scloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MyConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyConfigServiceApplication.class, args);
	}
}
