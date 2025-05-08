package com.example.demoProyect.api.v1.service;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.controller.exceptions.AccessDeniedCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidCredentialsCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.model.ApiUser;
import com.example.demoProyect.api.v1.model.UserRole;
import com.example.demoProyect.api.v1.repository.ApiKeyRepo;
import com.example.demoProyect.api.v1.repository.ApiUserRepo;

import jakarta.transaction.Transactional;

@Service
public class ApiUserService {
	
	private final ApiUserRepo apiUserRepo;
	private final PasswordEncoder passwordEnc;
	
	private final RequestDataParserService dataParser;
	


	
	public ApiUserService(ApiUserRepo apiUserDao, PasswordEncoder passwordEnc, RequestDataParserService dataParser) {
		this.apiUserRepo = apiUserDao;
		this.passwordEnc = passwordEnc;
		this.dataParser = dataParser;
	}
	
	@Transactional
	public String loginUserAndGetAccountKey(String username, String rawPassword) throws InvalidCredentialsCustEx, InvalidUserAccountKeyCustEx {
		ApiUser requestUser = this.apiUserRepo.findUserByUsernameWithQuery(username).orElseThrow( InvalidCredentialsCustEx::new );
		String storedHashPassword = requestUser.getPassword();
		
		if ( !this.passwordEnc.matches( rawPassword, storedHashPassword ) ) { throw new InvalidCredentialsCustEx(); }
		
		return requestUser.getUserAccountKey();
	}
	
	@Transactional
	public ApiUser getCurrentUser(String authorizationHeader) throws InvalidUserAccountKeyCustEx {
		String parsedKey = this.dataParser.parseAccountKeyFromHeader(authorizationHeader)
										.orElseThrow(InvalidUserAccountKeyCustEx::new);
		if ( !this.isAccountKeyValid(parsedKey) ) { throw new InvalidUserAccountKeyCustEx(); }

				
		return this.apiUserRepo.findById(parsedKey)
						.orElseThrow();
	}
	
	
	@Transactional
	public ApiUser createNewUser(String authorizationHeader, Map<String, String> requestBody) throws InvalidUserAccountKeyCustEx, InvalidDataCustEx, AccessDeniedCustEx {
		String parsedKey = this.dataParser.parseAccountKeyFromHeader(authorizationHeader)
				.orElseThrow(InvalidUserAccountKeyCustEx::new);
		if ( !this.isAccountKeyValid(parsedKey) ) { throw new InvalidUserAccountKeyCustEx(); }
		
		ApiUser creatorUser = this.getCurrentUser(authorizationHeader);
		if( !creatorUser.isHasAdmin() ) { throw new AccessDeniedCustEx(); }
		
		ApiUser createdUser = this.createNewUser(requestBody, creatorUser.getRole());
		
		this.apiUserRepo.save(createdUser);
		
		return createdUser;
		
		
		
		
	}
	
	
	//Private methods
	private ApiUser createNewUser(Map<String, String> requestBody, UserRole creatorRole) throws InvalidDataCustEx, AccessDeniedCustEx {
		ApiUser createdUser = new ApiUser();
		
		createdUser.setUserName( requestBody.get("username") );
		createdUser.setPassword( this.passwordEnc.encode( requestBody.get("password") ) );
		createdUser.setHasAdmin( Boolean.parseBoolean( requestBody.getOrDefault("isAdmin", "false") ) );
		
		createdUser.setAccountEnabled( Boolean.parseBoolean( requestBody.getOrDefault("accountEnabled", "true") ) );
		createdUser.setCreationDate(LocalDateTime.now());
		createdUser.setLastActivity(LocalDateTime.now());
		
		createdUser.setApiKeys(new ArrayList<>());
		createdUser.setUserAccountKey( this.generateRandomUserAccountKey()  );
		
		try {
			UserRole requestedRole = UserRole.valueOf( requestBody.get("role") );
			if (!creatorRole.equals( UserRole.EDITOR ) && !creatorRole.equals(requestedRole) ) { throw new AccessDeniedCustEx(); }
			createdUser.setRole( requestedRole );
		} catch (InvalidParameterException e) { throw new InvalidDataCustEx(); } 
		
		return createdUser;
	}
	
	private boolean isAccountKeyValid(String accountKey) {
		return this.apiUserRepo.findById(accountKey).isPresent();
	}
	
	
	private String generateRandomUserAccountKey() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
		
	}
	
	
	
	//OTHERS
	@Transactional
	public void createAdminUser() {
		if (this.apiUserRepo.findUserByUsernameWithQuery("admin").isEmpty()) {
			ApiUser newAdmin = new ApiUser(this.passwordEnc.encode( "admin" ), "admin", UserRole.EDITOR, true, true, LocalDateTime.now(), LocalDateTime.now(), null);
			apiUserRepo.save(newAdmin);
			
		}
	}

}
