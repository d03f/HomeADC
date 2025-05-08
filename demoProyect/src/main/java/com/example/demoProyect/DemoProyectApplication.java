package com.example.demoProyect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.demoProyect.api.v1.service.ApiUserService;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DemoProyectApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DemoProyectApplication.class, args);

	}

	@Bean
    CommandLineRunner run(ApiUserService userService, @Value("${admin.enabled:false}") boolean createAdmin) {
		
        return args -> {
            if (createAdmin) {
                userService.createAdminUser();
            }
        };
    }
	
	
}
