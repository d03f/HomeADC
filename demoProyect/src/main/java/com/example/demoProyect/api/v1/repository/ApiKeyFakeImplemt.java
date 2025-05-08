package com.example.demoProyect.api.v1.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demoProyect.api.v1.model.ApiKey;
import com.example.demoProyect.api.v1.model.ApiUser;
import com.example.demoProyect.api.v1.model.UserRole;


@Repository
public class ApiKeyFakeImplemt {
	
	
	private final List<ApiKey> exampleKeys = java.util.List.of();

	public boolean isApiKeyValid(String apiKey) {
		return !apiKey.equals("error");
	}

	public Optional<ApiKey> getApiKeyInfo(String apiKey) {
		ApiKey result = this.exampleKeys.get(0);
		result.setApiKeyValue(apiKey);
		return Optional.of( result );
	}

	public Optional<String[]> getApiKeysValuesFromAccountKey(String accountKey) {
		return Optional.of( new String[] {this.exampleKeys.get(0).getApiKeyValue(), this.exampleKeys.get(1).getApiKeyValue()} );
	}

	public boolean insertApiKey(ApiKey createdApiKey) {
		return true;
	}
	



}
