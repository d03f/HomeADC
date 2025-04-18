package com.example.demoProyect.api.v1.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demoProyect.api.v1.model.ApiKey;
import com.example.demoProyect.api.v1.model.ApiUser;
import com.example.demoProyect.api.v1.model.UserRole;


@Repository
public class ApiKeyFakeImplemt implements ApiKeyDao {
	
	
	private final List<ApiKey> exampleKeys = java.util.List.of(
			new ApiKey("apikey1adfjkasldfkjaklsfj", "apiKey1",
					null, UserRole.READER,
					true, null, 
					LocalDateTime.now(), LocalDateTime.now()),
	
			new ApiKey("apikey2asdfasdfasfeasdfa", "apiKey2",
					null, UserRole.WRITER,
					true, Optional.of(LocalDateTime.now().plusDays(10L)), 
					LocalDateTime.now(), LocalDateTime.now()));

	@Override
	public boolean isApiKeyValid(String apiKey) {
		return !apiKey.equals("error");
	}

	@Override
	public Optional<ApiKey> getApiKeyInfo(String apiKey) {
		ApiKey result = this.exampleKeys.get(0);
		result.setApiKeyValue(apiKey);
		return Optional.of( result );
	}

	@Override
	public Optional<String[]> getApiKeysValuesFromAccountKey(String accountKey) {
		// TODO Auto-generated method stub
		return Optional.of( new String[] {this.exampleKeys.get(0).getApiKeyValue(), this.exampleKeys.get(1).getApiKeyValue()} );
	}
	



}
