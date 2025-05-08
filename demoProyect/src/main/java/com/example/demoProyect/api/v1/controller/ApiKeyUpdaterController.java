package com.example.demoProyect.api.v1.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoProyect.api.v1.controller.exceptions.CustomException;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseError;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseOk;
import com.example.demoProyect.api.v1.service.ApiKeyUpdateService;

@RestController
@RequestMapping("/api/v1/users/me/apikey/update")
public class ApiKeyUpdaterController {
	
	private final ApiKeyUpdateService apiKeyService;
	
	public ApiKeyUpdaterController(ApiKeyUpdateService apiKeyRepo){
		this.apiKeyService = apiKeyRepo;
	}
	
	@PatchMapping("/name")
	public ResponseEntity<?> udpateName( @RequestBody Map<String, String> requestData ){
		try {			
			if (!requestData.containsKey(ApiKeyController.VERIFICATION_KEY_FIELDNAME)) { throw new InvalidDataCustEx(); }
			return CustomResponseOk.build( 
					this.apiKeyService.updateName(requestData.get(ApiKeyController.VERIFICATION_KEY_FIELDNAME), requestData.get("name"))
				);
		} catch ( CustomException e) {
			return CustomResponseError.build( e.getMessage() );
		} catch ( NullPointerException e) {
			return CustomResponseError.build( new InvalidDataCustEx().getMessage() );
		}
	}
	
	@PatchMapping("/enabled")
	public ResponseEntity<?> updateEnabled( @RequestBody Map<String, String> requestData ){
		try {			
			if (!requestData.containsKey(ApiKeyController.VERIFICATION_KEY_FIELDNAME)) { throw new InvalidDataCustEx(); }
			return CustomResponseOk.build( 
					this.apiKeyService.updateEnabled(requestData.get(ApiKeyController.VERIFICATION_KEY_FIELDNAME), Boolean.valueOf(requestData.get("enabled")))
				);
		} catch ( CustomException e) {
			return CustomResponseError.build( e.getMessage() );
		} catch ( NullPointerException e) {
			return CustomResponseError.build( new InvalidDataCustEx().getMessage() );
		}
	}
	
	@PatchMapping("/expirationdate")
	public ResponseEntity<?> updateExpirDate( @RequestBody Map<String, String> requestData ){
		try {			
			if (!requestData.containsKey(ApiKeyController.VERIFICATION_KEY_FIELDNAME)) { throw new InvalidDataCustEx(); }
			return CustomResponseOk.build( 
					this.apiKeyService.updateExpirDate(requestData.get(ApiKeyController.VERIFICATION_KEY_FIELDNAME), LocalDateTime.parse( requestData.get("expirationDate") ))
				);
		} catch ( CustomException e) {
			return CustomResponseError.build( e.getMessage() );
		} catch ( NullPointerException | DateTimeParseException e) {
			return CustomResponseError.build( new InvalidDataCustEx().getMessage() );
		}
	}

}
