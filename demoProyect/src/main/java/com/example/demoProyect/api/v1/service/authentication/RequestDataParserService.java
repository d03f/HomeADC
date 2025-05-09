package com.example.demoProyect.api.v1.service.authentication;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RequestDataParserService {
	
	public Optional<String> parseAccountKeyFromHeader(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) { return Optional.empty(); }
		
		
		return Optional.of(authorizationHeader.substring(7));
		
	}

}
