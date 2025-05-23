package com.homeadc.homeadc.api.v1.service.authentication.keys;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.homeadc.homeadc.api.v1.model.authentication.ApiKey;
import com.homeadc.homeadc.api.v1.model.authentication.ApiUser;
import com.homeadc.homeadc.api.v1.model.authentication.dto.ApiKeyDTO;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.homeadc.homeadc.api.v1.repository.authentication.ApiKeyRepo;
import com.homeadc.homeadc.api.v1.repository.authentication.ApiUserRepo;

import jakarta.transaction.Transactional;

@Service
public class ApiKeyUpdateService {
	
	private final ApiKeyRepo apiKeyRepo;
	private final ApiUserRepo apiUserRepo;

	public ApiKeyUpdateService(ApiKeyRepo apiKeyRepo, ApiUserRepo apiUserRepo) {
		this.apiKeyRepo = apiKeyRepo;
		this.apiUserRepo = apiUserRepo;
	}
	
	
	@Transactional
	public ApiKeyDTO updateName(String apiKey, String newName) throws InvalidApiKeyCustEx {
		ApiKey toBeChanged = this.apiKeyRepo.findByApiKeyValueAndKeyEnabledTrue(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		
		toBeChanged.setName(newName);
		this.apiKeyRepo.save(toBeChanged);
		
		return new ApiKeyDTO( toBeChanged );
	}
	
	@Transactional
	public ApiKeyDTO updateEnabled(String authToken,String apiKey, boolean newState) throws InvalidApiKeyCustEx, InvalidUserAccountKeyCustEx {
		ApiUser user = this.apiUserRepo.findById(authToken).orElseThrow(InvalidUserAccountKeyCustEx::new);
		ApiKey toBeChanged = this.apiKeyRepo.findByApiKeyValueAndOwner(apiKey, user).orElseThrow(InvalidApiKeyCustEx::new);
		
		toBeChanged.setKeyEnabled(newState);
		this.apiKeyRepo.save(toBeChanged);
		
		return new ApiKeyDTO( toBeChanged );
	}
	
	@Transactional
	public ApiKeyDTO updateExpirDate(String apiKey, LocalDateTime newDate) throws InvalidApiKeyCustEx {
		ApiKey toBeChanged = this.apiKeyRepo.findByApiKeyValueAndKeyEnabledTrue(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		
		toBeChanged.setExpirationDate(newDate);
		this.apiKeyRepo.save(toBeChanged);
		
		return new ApiKeyDTO( toBeChanged );
	}
	
	
	
	
	
}
