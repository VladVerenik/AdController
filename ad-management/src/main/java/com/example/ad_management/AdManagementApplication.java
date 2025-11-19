package com.example.ad_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class AdManagementApplication {
	 static void main(String[] args) {
		SpringApplication.run(AdManagementApplication.class, args);
	}
}
