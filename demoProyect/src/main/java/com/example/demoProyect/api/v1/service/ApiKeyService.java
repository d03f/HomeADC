package com.example.demoProyect.api.v1.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.controller.ApiKeyController;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.model.ApiKey;
import com.example.demoProyect.api.v1.model.UserRole;
import com.example.demoProyect.api.v1.repository.ApiKeyDao;
import com.example.demoProyect.api.v1.repository.ApiUserDao;

@Service
public class ApiKeyService {
	
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
	private final ApiKeyDao apiKeyDao;
	
	private final ApiUserDao apiUserDao;
	private final RequestDataParserService dataParser;
	
	public ApiKeyService(ApiKeyDao apiKeyDao, ApiUserDao apiUserDao, RequestDataParserService dataParser) {
		this.apiKeyDao = apiKeyDao;
		this.apiUserDao = apiUserDao;

		this.dataParser = dataParser;
		
	}
	
	public String[] getApiKeysFromAccountKey(String authorizationHeader) throws InvalidUserAccountKeyCustEx{
		String parsedKey = this.dataParser.parseAccountKeyFromHeader(authorizationHeader)
				.orElseThrow(InvalidUserAccountKeyCustEx::new);
		if (!this.apiUserDao.isAccountKeyValid(parsedKey)) { throw new InvalidUserAccountKeyCustEx(); }
		
		return this.apiKeyDao.getApiKeysValuesFromAccountKey(parsedKey).orElseThrow( () -> new InvalidUserAccountKeyCustEx() );
		
	}
	
	public ApiKey getApiKeyInfo(String apiKey) throws InvalidApiKeyCustEx {
		return this.apiKeyDao.getApiKeyInfo(apiKey).orElseThrow(() -> new InvalidApiKeyCustEx() );
	}
	
	public ApiKey generateApiKey(String authorizationHeader,  Map<String, String> requestBody) throws InvalidUserAccountKeyCustEx, InvalidDataCustEx{
		String parsedKey = this.dataParser.parseAccountKeyFromHeader(authorizationHeader)
				.orElseThrow(InvalidUserAccountKeyCustEx::new);
		if (!this.apiUserDao.isAccountKeyValid(parsedKey)) { throw new InvalidUserAccountKeyCustEx(); }
		
		ApiKey createdApiKey = new ApiKey();
		
		createdApiKey.setApiKeyValue( this.generateRandomApiKeyValue() );
		createdApiKey.setName( requestBody.get("name") );
		createdApiKey.setKeyEnabled( Boolean.parseBoolean( requestBody.getOrDefault("keyEnabled", "true                                      ") ) );

		createdApiKey.setCreationDate(LocalDateTime.now());
		createdApiKey.setLastActivity( LocalDateTime.now() );
		
		String expirationDate = requestBody.getOrDefault("expirationDate", null);
		if (expirationDate != null) { createdApiKey.setExpirationDate(   LocalDateTime.parse(expirationDate, DATE_TIME_FORMAT)   );}
		
		try { createdApiKey.setAccess( UserRole.valueOf( requestBody.get("access") ) );
		} catch (IllegalArgumentException e) { throw new InvalidDataCustEx(); }
		
		
		
		if ( !this.apiKeyDao.insertAPiKey(createdApiKey) ) { throw new InvalidDataCustEx(); } 
		return createdApiKey;
	}
	
	
	
	
	private Optional<String> getApiKeyFromBody(Map<String, String> requestBody ){
		return Optional.of( requestBody.get(ApiKeyController.VERIFICATION_KEY_FIELDNAME) );
		
	}

	
	private String generateRandomApiKeyValue() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
		
	}
	

}
