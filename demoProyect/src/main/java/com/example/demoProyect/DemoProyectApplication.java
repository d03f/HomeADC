package com.example.demoProyect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

import com.example.demoProyect.api.v1.service.ApiUserService;


@EnableRetry
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DemoProyectApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DemoProyectApplication.class, args);

	}

	@Bean
    CommandLineRunner run(ApiUserService userService, @Value("${admin.create-on-startup:false}") boolean createAdmin,
    		@Value("${admin.default-username}") String username,
    		@Value("${admin.default-password}") String password) {
		
        return args -> {
            if (createAdmin) {
                userService.createAdminUser(username, password);
            }
        };
    }
	
	
}
