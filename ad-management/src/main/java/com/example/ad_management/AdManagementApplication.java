package com.example.ad_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AdManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdManagementApplication.class, args);
	}
}
