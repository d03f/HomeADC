package com.example.demoProyect.api.v1.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoProyect.api.v1.controller.exceptions.CustomException;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseError;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseOk;
import com.example.demoProyect.api.v1.service.ApiKeyCleanupService;
import com.example.demoProyect.api.v1.service.ApiKeyService;

@RestController
@RequestMapping("/api/v1/users/me/apikey")
public class ApiKeyController {
	
	
	public static final String VERIFICATION_KEY_FIELDNAME = "verificationKey";
	
	
	private final ApiKeyService apiKeyService;
	public final ApiKeyCleanupService apiKeyCleanupService;
	
	public ApiKeyController(ApiKeyService apiKeyService, ApiKeyCleanupService apiKeyCleanupService) {
		this.apiKeyService = apiKeyService;
		this.apiKeyCleanupService = apiKeyCleanupService;
	}

	
	
	@GetMapping
	public ResponseEntity<?> getApiKeysOfCurrentUser( @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader ){
		try {
			return CustomResponseOk.build( 
					this.apiKeyService.getApiKeysFromAccountKey(authorizationHeader) 
				);
		} catch ( InvalidUserAccountKeyCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
		
	}
	
	@PostMapping
	public ResponseEntity<?> getApiKeyInfo( @RequestBody Map<String, String> requestData ){
		try {
			if (!requestData.containsKey(VERIFICATION_KEY_FIELDNAME)) { throw new InvalidDataCustEx(); }
			return CustomResponseOk.build( 
					this.apiKeyService.getApiKeyInfo(requestData.get(VERIFICATION_KEY_FIELDNAME))
				);
		} catch ( InvalidApiKeyCustEx | InvalidDataCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
		
	}
	
	@PostMapping("/generate")
	public ResponseEntity<?> generateApiKey ( @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData){
		try {
			
			return CustomResponseOk.build(
					this.apiKeyService.generateApiKey(authorizationHeader, requestData)		
				);
		} catch (CustomException e) {
			return CustomResponseError.build( e.getMessage() );
		}
		
		
		
	}
	
	@PostMapping("/remove")
	public ResponseEntity<?> deleteApiKey ( @RequestBody Map<String, String> requestData){
		try {
			if (!requestData.containsKey(VERIFICATION_KEY_FIELDNAME)) { throw new InvalidDataCustEx(); }
			return CustomResponseOk.build(
					this.apiKeyService.deleteApiKey(requestData.get(VERIFICATION_KEY_FIELDNAME))		
				);
		} catch (CustomException e) {
			return CustomResponseError.build( e.getMessage() );
		}
		
		
	}
	
	@GetMapping("/cleanup")
	public ResponseEntity<?> cleanUpKeys(){
		this.apiKeyCleanupService.removeExpiredApiKeys();
		return CustomResponseOk.build( "Cleaned all the outdated apiKeys!" );
	}
	
}
