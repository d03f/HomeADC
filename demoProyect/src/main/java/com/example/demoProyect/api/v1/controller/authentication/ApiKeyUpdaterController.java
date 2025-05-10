package com.example.demoProyect.api.v1.controller.authentication;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoProyect.api.v1.controller.responses.CustomResponseError;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseOk;
import com.example.demoProyect.api.v1.model.exceptions.CustomException;
import com.example.demoProyect.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.service.RequestDataParserService;
import com.example.demoProyect.api.v1.service.authentication.keys.ApiKeyUpdateService;

@RestController
@RequestMapping("/api/v1/users/me/apikey/update")
public class ApiKeyUpdaterController {
	
	private final ApiKeyUpdateService apiKeyService;
	private final RequestDataParserService parserService;
	
	public ApiKeyUpdaterController(ApiKeyUpdateService apiKeyRepo, RequestDataParserService parserService){
		this.apiKeyService = apiKeyRepo;
		this.parserService = parserService;
	}
	
	@PatchMapping("/name")
	public ResponseEntity<?> udpateName(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData ){
		try {			
			return CustomResponseOk.build( 
					this.apiKeyService.updateName(
							parserService.parseApiKeyFromHeader(authorizationHeader).orElseThrow(InvalidApiKeyCustEx::new),
							requestData.get("name"))
				);
		} catch ( CustomException e) {
			return CustomResponseError.build( e.getMessage() );
		} catch ( NullPointerException e) {
			return CustomResponseError.build( new InvalidDataCustEx().getMessage() );
		}
	}
	
	@PatchMapping("/enabled")
	public ResponseEntity<?> updateEnabled(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData ){
		try {			
			if (!requestData.containsKey(ApiKeyController.VERIFICATION_KEY_FIELDNAME)) { throw new InvalidDataCustEx(); }
			return CustomResponseOk.build( 
					this.apiKeyService.updateEnabled(
							this.parserService.parseAccountKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new),
							requestData.get(ApiKeyController.VERIFICATION_KEY_FIELDNAME), 
							Boolean.valueOf(requestData.get("enabled")))
				);
		} catch ( CustomException e) {
			return CustomResponseError.build( e.getMessage() );
		} catch ( NullPointerException e) {
			return CustomResponseError.build( new InvalidDataCustEx().getMessage() );
		}
	}
	
	@PatchMapping("/expirationdate")
	public ResponseEntity<?> updateExpirDate(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData ){
		try {			
			return CustomResponseOk.build( 
					this.apiKeyService.updateExpirDate(
							this.parserService.parseApiKeyFromHeader(authorizationHeader).orElseThrow(InvalidApiKeyCustEx::new),
							LocalDateTime.parse( requestData.get("expirationDate") ))
				);
		} catch ( CustomException e) {
			return CustomResponseError.build( e.getMessage() );
		} catch ( NullPointerException | DateTimeParseException e) {
			return CustomResponseError.build( new InvalidDataCustEx().getMessage() );
		}
	}

}
