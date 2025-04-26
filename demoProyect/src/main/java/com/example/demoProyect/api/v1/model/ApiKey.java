package com.example.demoProyect.api.v1.model;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApiKey {
	
	private String apiKeyValue;
	private String name;
	@JsonIgnore
	private ApiUser owner;
	
	private UserRole access;
	private boolean keyEnabled;
	
	private Optional<LocalDateTime> expirationDate;
	
	private LocalDateTime creationDate;
	private LocalDateTime lastActivity;
	
	
	public ApiKey() {}
	public ApiKey(String apiKeyValue, String name, ApiUser owner, UserRole access, boolean keyEnabled,
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


	public String getApiKeyValue() { return apiKeyValue; }
	public void setApiKeyValue(String apiKeyValue) { this.apiKeyValue = apiKeyValue; }


	public String getName() { return name; }
	public void setName(String name) { this.name = name; }


	public ApiUser getOwner() { return owner; }
	public void setOwner(ApiUser owner) { this.owner = owner; }


	public UserRole getAccess() { return access; }
	public void setAccess(UserRole access) { this.access = access; }


	public boolean isKeyEnabled() { return keyEnabled; }
	public void setKeyEnabled(boolean keyEnabled) { this.keyEnabled = keyEnabled; }


	public Optional<LocalDateTime> getExpirationDate() { return expirationDate; }
	public void setExpirationDate(LocalDateTime expirationDate) { this.expirationDate = Optional.of( expirationDate ); }


	public LocalDateTime getCreationDate() { return creationDate; }
	public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }


	public LocalDateTime getLastActivity() { return lastActivity; }
	public void setLastActivity(LocalDateTime lastActivity) { this.lastActivity = lastActivity; }
	
	

}
