package com.homeadc.homeadc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.homeadc.homeadc.api.v1.service.authentication.users.ApiUserService;


@EnableRetry
@EnableScheduling
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class HomeADCApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HomeADCApplication.class, args);

	}

	@Bean
    CommandLineRunner run(ApiUserService userService, @Value("${app.admin.create-on-startup:false}") boolean createAdmin,
    		@Value("${app.admin.default-username}") String adminUsername,
    		@Value("${app.admin.default-password}") String adminPassword) {
		
        return args -> {
            if (createAdmin) {
                userService.createAdminUser(adminUsername, adminPassword);
            }
        };
    }
	
	
}
