package com.homeadc.homeadc.api.v1.service.authentication.keys;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.homeadc.homeadc.api.v1.model.authentication.ApiKey;
import com.homeadc.homeadc.api.v1.model.authentication.ApiUser;
import com.homeadc.homeadc.api.v1.model.authentication.UserRole;
import com.homeadc.homeadc.api.v1.model.authentication.dto.ApiKeyDTO;
import com.homeadc.homeadc.api.v1.model.data.Sensor;
import com.homeadc.homeadc.api.v1.model.exceptions.AccessDeniedCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidDataCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.homeadc.homeadc.api.v1.repository.authentication.ApiKeyRepo;
import com.homeadc.homeadc.api.v1.repository.authentication.ApiUserRepo;
import com.homeadc.homeadc.api.v1.repository.data.SensorRepo;

import jakarta.transaction.Transactional;

@Service
public class ApiKeyService {
		
	private final ApiKeyRepo apiKeyRepo;
	private final SensorRepo sensorRepo;
	private final ApiUserRepo apiUserRepo;
	
	public ApiKeyService(ApiKeyRepo apiKeyRepo, ApiUserRepo apiUserDao, SensorRepo sensorRepo) {
		this.apiKeyRepo = apiKeyRepo; this.apiUserRepo = apiUserDao; this.sensorRepo = sensorRepo; 
	}
	
	@Transactional
	public String[] getApiKeysFromAccountKey(String parsedKey) throws InvalidUserAccountKeyCustEx{
		if (!this.apiUserRepo.existsByUserAccountKeyAndAccountEnabledTrue(parsedKey)) { throw new InvalidUserAccountKeyCustEx(); }
				
		List<String> apiKeyValues = this.apiKeyRepo.findApiKeyValuesByOwnerUserAccountKey(parsedKey);
		
		if (!apiKeyValues.isEmpty()) { return apiKeyValues.toArray(size -> new String[size]); }
		return new String[] {};
		
	}
	
	@Transactional
	public ApiKeyDTO getApiKeyInfo(String apiKey) throws InvalidApiKeyCustEx {
		return new ApiKeyDTO(
				this.apiKeyRepo.findById(apiKey).orElseThrow(InvalidApiKeyCustEx::new ) );
	}
	
	
	@Transactional
	public ApiKeyDTO generateApiKey(String parsedKey,  Map<String, String> requestBody) throws InvalidUserAccountKeyCustEx, InvalidDataCustEx, AccessDeniedCustEx{

		if (!this.apiUserRepo.existsByUserAccountKeyAndAccountEnabledTrue(parsedKey)) { throw new InvalidUserAccountKeyCustEx(); }
		
		
		
		ApiKey createdApiKey = new ApiKey();
		
		//Creation
		ApiUser owner = this.apiUserRepo.findById(parsedKey).orElseThrow();
		createdApiKey.setOwner( owner );
		
		createdApiKey.setName( requestBody.get("name") );
		createdApiKey.setKeyEnabled( Boolean.parseBoolean( requestBody.getOrDefault("keyEnabled", "true") ) );

		createdApiKey.setCreationDate(LocalDateTime.now());
		createdApiKey.setLastActivity( LocalDateTime.now() );
		
		String expirationDate = requestBody.getOrDefault("expirationDate", null);
		if (expirationDate != null) { createdApiKey.setExpirationDate( LocalDateTime.parse( expirationDate ));}
		
		
		try { 
			createdApiKey.setAccess( UserRole.valueOf( requestBody.get("access") ) );
			
		} catch (IllegalArgumentException e) { throw new InvalidDataCustEx(); }

		//Access verification
		if (this.cantAccessThatRole(createdApiKey, owner)){ throw new AccessDeniedCustEx(); }

		this.apiKeyRepo.save(createdApiKey);
		return new ApiKeyDTO(createdApiKey);
	}
	
	@Transactional
	public ApiKeyDTO deleteApiKey(String apiKey) throws InvalidApiKeyCustEx {
		ApiKey toBeDeleted = this.apiKeyRepo.findById(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		
		
		List<Sensor> linkedSensors = this.sensorRepo.findByAllowedApiKeys_ApiKeyValue(apiKey);
		
		for (Sensor currSensor : linkedSensors) {
			currSensor.removeAllowedApiKey(toBeDeleted);
			this.sensorRepo.save(currSensor);
		}
		
		
		this.apiKeyRepo.delete(toBeDeleted);
		
		return new ApiKeyDTO(toBeDeleted);
	}

	
	//Private custom methods
	private boolean cantAccessThatRole(ApiKey createdApiKey, ApiUser owner) {
		return !createdApiKey.getAccess().equals(owner.getRole()) && !owner.getRole().equals(UserRole.EDITOR);
	}


}
