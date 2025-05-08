package com.example.demoProyect.api.v1.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demoProyect.api.v1.model.ApiKey;
import com.example.demoProyect.api.v1.model.ApiUser;
import com.example.demoProyect.api.v1.model.UserRole;
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
	
}
