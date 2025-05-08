package com.example.demoProyect.api.v1.model.dto;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.demoProyect.api.v1.model.ApiUser;
import com.example.demoProyect.api.v1.model.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApiKeyDTO {
	
	private String apiKeyValue;
	private String name;
	@JsonIgnore
	private ApiUser owner;
	
	private UserRole access;
	private boolean keyEnabled;
	
	private Optional<LocalDateTime> expirationDate;
	
	private LocalDateTime creationDate;
	private LocalDateTime lastActivity;
	
	
	public ApiKeyDTO() {}
	public ApiKeyDTO(String apiKeyValue, String name, ApiUser owner, UserRole access, boolean keyEnabled,
			Optional<LocalDateTime> expirationDate, LocalDateTime creationDate, LocalDateTime lastActivity) {
		
		this.apiKeyValue = apiKeyValue;
		this.name = name;
		this.owner = owner;
		this.access = access;
		this.keyEnabled = keyEnabled;
		this.expirationDate = expirationDate;
		this.creationDate = creationDate;
		this.lastActivity = lastActivity;
	}
	
	
	
	
	
	

}
