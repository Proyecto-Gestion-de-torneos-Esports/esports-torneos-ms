package com.torneos.torneos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TorneosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorneosApplication.class, args);
	}

}
