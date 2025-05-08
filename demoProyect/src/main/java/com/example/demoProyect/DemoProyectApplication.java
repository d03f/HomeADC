package com.example.demoProyect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DemoProyectApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DemoProyectApplication.class, args);

	}

	
	
}
