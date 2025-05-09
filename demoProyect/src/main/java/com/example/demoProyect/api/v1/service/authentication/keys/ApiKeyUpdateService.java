package com.example.demoProyect.api.v1.service.authentication.keys;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.model.authentication.ApiKey;
import com.example.demoProyect.api.v1.model.authentication.dto.ApiKeyDTO;
import com.example.demoProyect.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.repository.authentication.ApiKeyRepo;

import jakarta.transaction.Transactional;

@Service
public class ApiKeyUpdateService {

	private final ApiKeyRepo apiKeyRepo;

	public ApiKeyUpdateService(ApiKeyRepo apiKeyRepo) {
		this.apiKeyRepo = apiKeyRepo;
	}
	
	
	@Transactional
	public ApiKeyDTO updateName(String apiKey, String newName) throws InvalidApiKeyCustEx {
		ApiKey toBeChanged = this.apiKeyRepo.findById(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		
		toBeChanged.setName(newName);
		this.apiKeyRepo.save(toBeChanged);
		
		return new ApiKeyDTO( toBeChanged );
	}
	
	@Transactional
	public ApiKeyDTO updateEnabled(String apiKey, boolean newState) throws InvalidApiKeyCustEx {
		ApiKey toBeChanged = this.apiKeyRepo.findById(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		
		toBeChanged.setKeyEnabled(newState);
		this.apiKeyRepo.save(toBeChanged);
		
		return new ApiKeyDTO( toBeChanged );
	}
	
	@Transactional
	public ApiKeyDTO updateExpirDate(String apiKey, LocalDateTime newDate) throws InvalidApiKeyCustEx {
		ApiKey toBeChanged = this.apiKeyRepo.findById(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		
		toBeChanged.setExpirationDate(newDate);
		this.apiKeyRepo.save(toBeChanged);
		
		return new ApiKeyDTO( toBeChanged );
	}
	
	
	
	
	
}
