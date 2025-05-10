package com.example.demoProyect.api.v1.controller.authentication;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoProyect.api.v1.controller.responses.CustomResponseError;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseOk;
import com.example.demoProyect.api.v1.model.exceptions.CustomException;
import com.example.demoProyect.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidCredentialsCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.service.RequestDataParserService;
import com.example.demoProyect.api.v1.service.authentication.keys.ApiKeyCleanupService;
import com.example.demoProyect.api.v1.service.authentication.keys.ApiKeyService;

@RestController
@RequestMapping("/api/v1/users/me/apikey")
public class ApiKeyController {
	
	
	public static final String VERIFICATION_KEY_FIELDNAME = "apiKeyValue";
	
	
	private final ApiKeyService apiKeyService;
	public final ApiKeyCleanupService apiKeyCleanupService;
	private RequestDataParserService dataParser;
	
	public ApiKeyController(ApiKeyService apiKeyService, ApiKeyCleanupService apiKeyCleanupService, RequestDataParserService dataParser) {
		this.apiKeyService = apiKeyService;
		this.apiKeyCleanupService = apiKeyCleanupService;
		this.dataParser = dataParser;
	}

	
	
	@GetMapping
	public ResponseEntity<?> getApiKeysOfCurrentUser( @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader ){
		try {
			Optional<String> bearerToken = this.dataParser.parseAccountKeyFromHeader(authorizationHeader);
			Optional<String> apiToken = this.dataParser.parseApiKeyFromHeader(authorizationHeader);
			if (bearerToken.isPresent()) {
				return CustomResponseOk.build( this.apiKeyService.getApiKeysFromAccountKey(authorizationHeader)  );
			} else if( apiToken.isPresent()) {
				return CustomResponseOk.build(  this.apiKeyService.getApiKeyInfo(apiToken.get()) );
			} else {
				return CustomResponseError.build( new InvalidCredentialsCustEx().getMessage() );				
			}
		} catch ( InvalidUserAccountKeyCustEx | InvalidApiKeyCustEx e) {
			return CustomResponseError.build(e);
		}
		
	}

	
	@PostMapping
	public ResponseEntity<?> generateApiKey ( @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData){
		try {
			
			return CustomResponseOk.build(
					this.apiKeyService.generateApiKey(
							this.dataParser.parseAccountKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new),
							requestData)		
				);
		} catch (CustomException e) {
			return CustomResponseError.build(e);
		}
		
		
		
	}
	
	
	
	@DeleteMapping
	public ResponseEntity<?> deleteApiKey (@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
		try {
			return CustomResponseOk.build(
					this.apiKeyService.deleteApiKey(
							this.dataParser.parseApiKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new)
							)		
				);
		} catch (CustomException e) {
			return CustomResponseError.build( e );
		}
		
		
	}
	
	@GetMapping("/cleanup")
	public ResponseEntity<?> cleanUpKeys(){
		this.apiKeyCleanupService.removeExpiredApiKeys();
		return CustomResponseOk.build( "Cleaned all the outdated apiKeys!" );
	}
	
}
