package com.dev.wistlist_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WistlistAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WistlistAppApplication.class, args);
	}

}
