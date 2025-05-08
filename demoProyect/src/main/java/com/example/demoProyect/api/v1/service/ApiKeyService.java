package com.example.demoProyect.api.v1.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.controller.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.model.ApiKey;
import com.example.demoProyect.api.v1.model.ApiUser;
import com.example.demoProyect.api.v1.model.UserRole;
import com.example.demoProyect.api.v1.model.dto.ApiKeyDTO;
import com.example.demoProyect.api.v1.repository.ApiKeyRepo;
import com.example.demoProyect.api.v1.repository.ApiUserRepo;

import jakarta.transaction.Transactional;

@Service
public class ApiKeyService {
	
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
	private final ApiKeyRepo apiKeyRepo;
	
	private final ApiUserRepo apiUserRepo;
	private final RequestDataParserService dataParser;
	
	public ApiKeyService(ApiKeyRepo apiKeyRepo, ApiUserRepo apiUserDao, RequestDataParserService dataParser) {
		this.apiKeyRepo = apiKeyRepo; this.apiUserRepo = apiUserDao;  this.dataParser = dataParser; 
	}
	
	@Transactional
	public String[] getApiKeysFromAccountKey(String authorizationHeader) throws InvalidUserAccountKeyCustEx{
		String parsedKey = this.dataParser.parseAccountKeyFromHeader(authorizationHeader)
				.orElseThrow(InvalidUserAccountKeyCustEx::new);
		
		if (!this.apiUserRepo.existsByUserAccountKeyAndAccountEnabledTrue(parsedKey)) { throw new InvalidUserAccountKeyCustEx(); }
		
		
		List<String> apiKeyValues = this.apiKeyRepo.findApiKeyValuesByOwnerUserAccountKey(parsedKey);
		
		if (!apiKeyValues.isEmpty()) {
			return apiKeyValues.toArray(size -> new String[size]);
		}
		
		return new String[] {};
		
	}
	
	@Transactional
	public ApiKeyDTO getApiKeyInfo(String apiKey) throws InvalidApiKeyCustEx {
		return new ApiKeyDTO(
				this.apiKeyRepo.findById(apiKey).orElseThrow(() -> new InvalidApiKeyCustEx() ) );
	}
	
	
	@Transactional
	public ApiKeyDTO generateApiKey(String authorizationHeader,  Map<String, String> requestBody) throws InvalidUserAccountKeyCustEx, InvalidDataCustEx{
		String parsedKey = this.dataParser.parseAccountKeyFromHeader(authorizationHeader)
				.orElseThrow(InvalidUserAccountKeyCustEx::new);
		if (!this.apiUserRepo.existsByUserAccountKeyAndAccountEnabledTrue(parsedKey)) { throw new InvalidUserAccountKeyCustEx(); }
		
		
		
		ApiKey createdApiKey = new ApiKey();
		
		ApiUser owner = this.apiUserRepo.findById(parsedKey).orElseThrow();
		createdApiKey.setOwner( owner );
		
		createdApiKey.setName( requestBody.get("name") );
		createdApiKey.setKeyEnabled( Boolean.parseBoolean( requestBody.getOrDefault("keyEnabled", "true                                      ") ) );

		createdApiKey.setCreationDate(LocalDateTime.now());
		createdApiKey.setLastActivity( LocalDateTime.now() );
		
		String expirationDate = requestBody.getOrDefault("expirationDate", null);
		if (expirationDate != null) { createdApiKey.setExpirationDate(   LocalDateTime.parse(expirationDate, DATE_TIME_FORMAT)   );}
		
		try { createdApiKey.setAccess( UserRole.valueOf( requestBody.get("access") ) );
		} catch (IllegalArgumentException e) { throw new InvalidDataCustEx(); }

		this.apiKeyRepo.save(createdApiKey);
		return new ApiKeyDTO(createdApiKey);
	}
	
	@Transactional
	public ApiKeyDTO deleteApiKey(String apiKey) throws InvalidApiKeyCustEx {
		ApiKey toBeDeleted = this.apiKeyRepo.findById(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		this.apiKeyRepo.delete(toBeDeleted);
		
		return new ApiKeyDTO(toBeDeleted);
	}



}
