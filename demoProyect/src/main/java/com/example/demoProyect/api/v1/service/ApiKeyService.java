package com.example.demoProyect.api.v1.service;

import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.controller.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.model.ApiKey;
import com.example.demoProyect.api.v1.repository.ApiKeyDao;
import com.example.demoProyect.api.v1.repository.ApiUserDao;
import com.example.demoProyect.api.v1.repository.ApiUserFakeImplement;

@Service
public class ApiKeyService {
	
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
	

}
