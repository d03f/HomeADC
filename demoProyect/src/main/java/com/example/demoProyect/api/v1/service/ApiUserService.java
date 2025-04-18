package com.example.demoProyect.api.v1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.controller.exceptions.InvalidCredentialsCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.model.ApiUser;
import com.example.demoProyect.api.v1.repository.ApiUserDao;

@Service
public class ApiUserService {
	
	private final ApiUserDao apiUserDao;
	
	public ApiUserService(ApiUserDao apiUserDao) {
		this.apiUserDao = apiUserDao;
	}
	
	public String loginUserAndGetAccountKey(String username, String password) throws InvalidCredentialsCustEx, InvalidUserAccountKeyCustEx {
		//TODO the hashing
		Optional<String> storedPasswd = this.apiUserDao.getPasswordOfUsername(username);
		//TODO take a look at the commented line
		if (storedPasswd.isEmpty() /* || storedPasswd.get() != password*/) { throw new InvalidCredentialsCustEx();}
		
		
		Optional<String> accountKey = this.apiUserDao.getUserAccountKey(username);
		if (accountKey.isEmpty()) { throw new InvalidUserAccountKeyCustEx(); }
		
		return accountKey.get();
	}
	
	public ApiUser getCurrentUser(String authorizationHeader) throws InvalidUserAccountKeyCustEx {
		Optional<String> parsedKey = this.parseAccountKeyFromHeader(authorizationHeader);
		if (parsedKey.isEmpty()|| !this.isAccountKeyValid(parsedKey.get())) { throw new InvalidUserAccountKeyCustEx(); }

				
		Optional<ApiUser> data = this.apiUserDao.getApiUserFromAccountKey(parsedKey.get());
		return data.get();
	}
	
	public List<String> getApiKeysFromAccountKey(String authorizationHeader) throws InvalidUserAccountKeyCustEx{
		Optional<String> parsedKey = this.parseAccountKeyFromHeader(authorizationHeader);
		if (parsedKey.isEmpty() || !this.isAccountKeyValid(parsedKey.get())) { throw new InvalidUserAccountKeyCustEx(); }
		
		return this.apiUserDao.getApiKeysFromAccountKey(parsedKey.get());
		
	}
	
	
	
	
	
	//Private methods
	private boolean isAccountKeyValid(String accountKey) {
		return this.apiUserDao.isAccountKeyValid(accountKey);
	}
	
	private Optional<String> parseAccountKeyFromHeader(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) { return Optional.empty(); }
		
		
		return Optional.of(authorizationHeader.substring(7));
		
	}
	

}
