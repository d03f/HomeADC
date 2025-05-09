package com.example.demoProyect.api.v1.service.authentication.keys;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.model.authentication.ApiKey;
import com.example.demoProyect.api.v1.repository.authentication.ApiKeyRepo;

import jakarta.transaction.Transactional;

@Service
public class ApiKeyCleanupService {
	private final ApiKeyRepo apiKeyRepo;
	
	public ApiKeyCleanupService(ApiKeyRepo apiKeyRepo) { this.apiKeyRepo = apiKeyRepo; }

	
	 @Scheduled(cron = "${app.scheduling.apikey-cleanup.cron}") // Example: run every day at 2:00 AM
	 @Transactional // Ensure deletion happens within a transaction
	 public void removeExpiredApiKeys() {
		 LocalDateTime now = LocalDateTime.now();
	
	        List<ApiKey> expiredKeys = this.apiKeyRepo.findByExpirationDateLessThanEqual(now);
	
	        if (!expiredKeys.isEmpty()) {
	            apiKeyRepo.deleteAll(expiredKeys);
	        }
	    }
}
