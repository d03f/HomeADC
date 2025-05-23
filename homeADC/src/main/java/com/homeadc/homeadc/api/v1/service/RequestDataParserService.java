package com.homeadc.homeadc.api.v1.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RequestDataParserService {
	
	private static final String API_KEY_SCHEME = "ApiKey ";
	
	public Optional<String> parseAccountKeyFromHeader(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) { return Optional.empty(); }
		return Optional.of(authorizationHeader.substring(7));
		
	}

	public Optional<String> parseApiKeyFromHeader(String authorizationHeader){
		if (authorizationHeader == null || !authorizationHeader.startsWith(API_KEY_SCHEME)) { return Optional.empty(); }
		
		return Optional.of( authorizationHeader.substring( API_KEY_SCHEME.length() ) );
	}
}
