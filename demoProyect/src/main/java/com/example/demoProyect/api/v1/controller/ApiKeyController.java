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

import com.example.demoProyect.api.v1.controller.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseError;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseOk;
import com.example.demoProyect.api.v1.service.ApiKeyService;

@RestController
@RequestMapping("/api/v1/users/me/apikey")
public class ApiKeyController {
	
	private final ApiKeyService apiKeyService;
	
	public ApiKeyController(ApiKeyService apiKeyService) {
		this.apiKeyService = apiKeyService;
	}

	
	
	@GetMapping
	public ResponseEntity<?> getApiKeysOfCurrentUser( @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
		try {
			return CustomResponseOk.build( 
					this.apiKeyService.getApiKeysFromAccountKey(authorizationHeader) 
				);
		} catch ( InvalidUserAccountKeyCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
		
	}
	
	@PostMapping
	public ResponseEntity<?> getApiKeyInfo( @RequestBody Map<String, String> requestData){
		System.out.println("hola");
		try {
			return CustomResponseOk.build( 
					this.apiKeyService.getApiKeyInfo(requestData.get("key"))
				);
		} catch ( InvalidApiKeyCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
		
	}
	
}
