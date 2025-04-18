package com.example.demoProyect.api.v1.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demoProyect.api.v1.model.ApiKey;
import com.example.demoProyect.api.v1.model.ApiUser;
import com.example.demoProyect.api.v1.model.UserRole;


@Repository
public class ApiUserFakeImplemt implements ApiUserDao {
	
	
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
	public boolean isAccountKeyValid(String accountKey) {
		return !"error".equals(accountKey);
	}
	

	@Override
	public Optional<ApiUser> getApiUserFromAccountKey(String userAcountKey) {
		return Optional.of(  new ApiUser(
				"paswd", "username", 
				userAcountKey, UserRole.EDITOR, 
				false, true, 
				LocalDateTime.now(),
				LocalDateTime.now(), 
				new java.util.ArrayList<>() )
			);
	}
	
	


	@Override
	public List<String> getApiKeysFromAccountKey(String accountKey) {
		return this.exampleKeys.stream().map(ApiKey::getApiKeyValue).toList();
	}




	@Override
	public Optional<String> getPasswordOfUsername(String searchUsername) {
		return "error".equals(searchUsername) ? Optional.empty() :Optional.of("passwrod");
	}




	@Override
	public Optional<String> getUserAccountKey(String searchUserName) {
		return Optional.of("userAccountKeyexamplelalsdkfj");
	}







}
