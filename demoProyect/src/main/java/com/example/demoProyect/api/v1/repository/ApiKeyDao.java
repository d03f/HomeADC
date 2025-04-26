package com.example.demoProyect.api.v1.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demoProyect.api.v1.model.ApiKey;


@Repository
public interface ApiKeyDao {
	
	public boolean isApiKeyValid(String apiKey);
	public Optional<ApiKey> getApiKeyInfo(String apiKey);
	
	public Optional<String[]> getApiKeysValuesFromAccountKey(String accountKey);
	
	public boolean insertApiKey(ApiKey createdApiKey);


}
