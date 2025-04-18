package com.example.demoProyect.api.v1.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"userName", "role", "hasAdmin", "userAccountKey", "lastActivity", "creationDate", "accountEnabled"})
public class ApiUser {
	
	private String userName;
	private String userAccountKey;
	@JsonIgnore
	private String password;
	
	private UserRole role;
	private boolean hasAdmin;
	
	private boolean accountEnabled; 
	
	private LocalDateTime lastActivity;
	private LocalDateTime creationDate;
	
	@JsonIgnore
	private List<ApiKey> apiKeys;
	
	
	
	public ApiUser() {}
	public ApiUser(String password, String userName, String userAccountKey, UserRole role, boolean isAdmin,
			boolean accountEnabled, LocalDateTime lastActivity, LocalDateTime creationDate, List<ApiKey> apiKeys) {
		super();
		this.password = password;
		this.userName = userName;
		this.userAccountKey = userAccountKey;
		this.role = role;
		this.hasAdmin = isAdmin;
		this.accountEnabled = accountEnabled;
		this.lastActivity = lastActivity;
		this.creationDate = creationDate;
		this.apiKeys = apiKeys;
	}
	public String getPassword() { return password;}
	public void setPassword(String password) { this.password = password; }
	
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
	
	public String getUserAccountKey() { return userAccountKey; }
	public void setUserAccountKey(String userAccountKey) { this.userAccountKey = userAccountKey; }
	
	public UserRole getRole() { return role; }
	public void setRole(UserRole role) { this.role = role; }
	
	public boolean isHasAdmin() { return hasAdmin; }
	public void setHasAdmin(boolean hasAdmin) { this.hasAdmin = hasAdmin; }
	
	public boolean isAccountEnabled() { return accountEnabled; }
	public void setAccountEnabled(boolean accountEnabled) { this.accountEnabled = accountEnabled; }
	
	public LocalDateTime getLastActivity() { return lastActivity; }
	public void setLastActivity(LocalDateTime lastActivity) { this.lastActivity = lastActivity; }
	
	public LocalDateTime getCreationDate() { return creationDate; }
	public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
	
	public List<ApiKey> getApiKeys() { return apiKeys; }
	public void setApiKeys(List<ApiKey> apiKeys) { this.apiKeys = apiKeys; }

	
	
	
	
}

