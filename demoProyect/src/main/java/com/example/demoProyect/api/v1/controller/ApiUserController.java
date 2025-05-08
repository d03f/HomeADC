package com.example.demoProyect.api.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.example.demoProyect.api.v1.controller.exceptions.CustomException;
import com.example.demoProyect.api.v1.controller.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseError;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseOk;
import com.example.demoProyect.api.v1.service.ApiUserService;

@RestController
@RequestMapping("/api/v1/users/")
public class ApiUserController {
	
	private final ApiUserService apiUserService;
	
	public ApiUserController(ApiUserService apiUserService) {
		this.apiUserService = apiUserService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> getAccountKeyOfUserPasswd(@RequestBody Map<String, String> requestData) {
		String username = requestData.get("username");
		String password = requestData.get("password");
		
		try {
			return CustomResponseOk.build( 
					java.util.Map.of("userAccountKey", this.apiUserService.loginUserAndGetAccountKey(username, password) )			
				);
		} catch (CustomException e) {
			return CustomResponseError.build( e.getMessage() );
		}
		
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser( @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {		
		try {
			return CustomResponseOk.build( 
					this.apiUserService.getCurrentUser(authorizationHeader) 
				);
		} catch ( InvalidUserAccountKeyCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	
	
	@PostMapping("/createuser")
	public ResponseEntity<?> createNewUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData){
		try {
			return CustomResponseOk.build(
					this.apiUserService.createNewUser(authorizationHeader, requestData)
				);
		} catch ( CustomException e) { return CustomResponseError.build(e.getMessage()); }
		
	}

	
	
	
	
	
	
	
}
