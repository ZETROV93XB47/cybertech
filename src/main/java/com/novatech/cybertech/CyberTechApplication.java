package com.novatech.cybertech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CyberTechApplication {

	public static void main(String[] args) {
		SpringApplication.run(CyberTechApplication.class, args);
	}

}
