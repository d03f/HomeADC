package com.example.demoProyect.api.v1.service.authentication.users;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.model.authentication.ApiUser;
import com.example.demoProyect.api.v1.model.authentication.UserRole;
import com.example.demoProyect.api.v1.model.authentication.dto.ApiUserDTO;
import com.example.demoProyect.api.v1.model.authentication.exceptions.AccessDeniedCustEx;
import com.example.demoProyect.api.v1.model.authentication.exceptions.DuplicatedEntryCustEx;
import com.example.demoProyect.api.v1.model.authentication.exceptions.InvalidCredentialsCustEx;
import com.example.demoProyect.api.v1.model.authentication.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.model.authentication.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.repository.authentication.ApiUserRepo;
import com.example.demoProyect.api.v1.service.authentication.RequestDataParserService;

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
	public ApiUserDTO getCurrentUser(String authorizationHeader) throws InvalidUserAccountKeyCustEx {
		String parsedKey = this.dataParser.parseAccountKeyFromHeader(authorizationHeader)
										.orElseThrow(InvalidUserAccountKeyCustEx::new);
		if ( !this.isAccountKeyValid(parsedKey) ) { throw new InvalidUserAccountKeyCustEx(); }

				
		return new ApiUserDTO( this.apiUserRepo.findById(parsedKey)
						.orElseThrow() );
	}
	
	
	@Transactional
	public ApiUserDTO createNewUser(String authorizationHeader, Map<String, String> requestBody) throws InvalidUserAccountKeyCustEx, InvalidDataCustEx, AccessDeniedCustEx, DuplicatedEntryCustEx {
		String parsedKey = this.dataParser.parseAccountKeyFromHeader(authorizationHeader)
				.orElseThrow(InvalidUserAccountKeyCustEx::new);
		if ( !this.isAccountKeyValid(parsedKey) ) { throw new InvalidUserAccountKeyCustEx(); }
		if (!this.apiUserRepo.findUserByUsernameWithQuery(requestBody.get("username")).isEmpty()  ) { throw new DuplicatedEntryCustEx();}
		
		
		ApiUser creatorUser = this.apiUserRepo.findById(parsedKey).orElseThrow();
		
		
		if( !creatorUser.isHasAdmin() ) { throw new AccessDeniedCustEx(); }
		
		ApiUser createdUser = this.createNewUser(requestBody, creatorUser.getRole());
		this.apiUserRepo.save(createdUser);
		
		return new ApiUserDTO( createdUser );
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
	
	
	
	
	
	//OTHERS
	@Transactional
	public void createAdminUser(String username, String password) {
		if (this.apiUserRepo.findUserByUsernameWithQuery(username).isEmpty()) {
			ApiUser newAdmin = new ApiUser(this.passwordEnc.encode( password ), username, UserRole.EDITOR, true, true, LocalDateTime.now(), LocalDateTime.now(), null, null);
			apiUserRepo.save(newAdmin);
			
		}
	}

}
