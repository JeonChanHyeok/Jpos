package com.example.jpos_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class JposServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JposServerApplication.class, args);
	}

}
