package com.example.demoProyect.api.v1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.controller.exceptions.InvalidCredentialsCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.model.ApiUser;
import com.example.demoProyect.api.v1.repository.ApiUserDao;

@Service
public class ApiUserService {
	
	private final ApiUserDao apiUserDao;
	private final PasswordEncoder passwordEnc;
	
	public ApiUserService(ApiUserDao apiUserDao, PasswordEncoder passwordEnc) {
		this.apiUserDao = apiUserDao;
		this.passwordEnc = passwordEnc;
	}
	
	public String loginUserAndGetAccountKey(String username, String rawPassword) throws InvalidCredentialsCustEx, InvalidUserAccountKeyCustEx {
		String storedHashPassword = this.apiUserDao.getPasswordOfUsername(username)
											.orElseThrow( InvalidUserAccountKeyCustEx::new );
		
		if ( !this.passwordEnc.matches( rawPassword, storedHashPassword ) ) { throw new InvalidCredentialsCustEx(); }
		
		return this.apiUserDao.getUserAccountKey(username)
						.orElseThrow( InvalidUserAccountKeyCustEx::new );
	}
	
	public ApiUser getCurrentUser(String authorizationHeader) throws InvalidUserAccountKeyCustEx {
		String parsedKey = this.parseAccountKeyFromHeader(authorizationHeader)
										.orElseThrow(InvalidUserAccountKeyCustEx::new);

		if ( !this.isAccountKeyValid(parsedKey) ) { throw new InvalidUserAccountKeyCustEx(); }
				
		return this.apiUserDao.getApiUserFromAccountKey(parsedKey)
						.orElseThrow();
	}
	
	public List<String> getApiKeysFromAccountKey(String authorizationHeader) throws InvalidUserAccountKeyCustEx{
		String parsedKey = this.parseAccountKeyFromHeader(authorizationHeader)
				.orElseThrow(InvalidUserAccountKeyCustEx::new);
		if (!this.isAccountKeyValid(parsedKey)) { throw new InvalidUserAccountKeyCustEx(); }
		
		return this.apiUserDao.getApiKeysFromAccountKey(parsedKey);
		
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
