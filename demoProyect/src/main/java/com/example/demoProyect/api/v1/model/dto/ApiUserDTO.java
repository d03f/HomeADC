package com.example.demoProyect.api.v1.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demoProyect.api.v1.model.ApiKey;
import com.example.demoProyect.api.v1.model.ApiUser;
import com.example.demoProyect.api.v1.model.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"userName", "role", "hasAdmin", "userAccountKey", "lastActivity", "creationDate", "accountEnabled"})
public class ApiUserDTO {

	private String userName;
	private String userAccountKey;
	
	private UserRole role;
	private boolean hasAdmin;
	
	private boolean accountEnabled; 
	
	private LocalDateTime lastActivity;
	private LocalDateTime creationDate;
	
	@JsonIgnore
	private List<ApiKey> apiKeys;
	
	
	
	public ApiUserDTO() {}
	
	public ApiUserDTO(ApiUser apiUserData) {
		super();
		this.userName = apiUserData.getUserName();
		this.userAccountKey = apiUserData.getUserAccountKey();
		this.role = apiUserData.getRole();
		this.hasAdmin = apiUserData.isHasAdmin();
		this.accountEnabled = apiUserData.isAccountEnabled();
		this.lastActivity = apiUserData.getLastActivity();
		this.creationDate = apiUserData.getCreationDate();
		this.apiKeys = apiUserData.getApiKeys();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccountKey() {
		return userAccountKey;
	}

	public void setUserAccountKey(String userAccountKey) {
		this.userAccountKey = userAccountKey;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public boolean isHasAdmin() {
		return hasAdmin;
	}

	public void setHasAdmin(boolean hasAdmin) {
		this.hasAdmin = hasAdmin;
	}

	public boolean isAccountEnabled() {
		return accountEnabled;
	}

	public void setAccountEnabled(boolean accountEnabled) {
		this.accountEnabled = accountEnabled;
	}

	public LocalDateTime getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(LocalDateTime lastActivity) {
		this.lastActivity = lastActivity;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public List<ApiKey> getApiKeys() {
		return apiKeys;
	}

	public void setApiKeys(List<ApiKey> apiKeys) {
		this.apiKeys = apiKeys;
	}
	
	
	
	
}
