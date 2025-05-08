package com.example.demoProyect.api.v1.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demoProyect.api.v1.model.ApiKey;
import com.example.demoProyect.api.v1.model.UserRole;

public class ApiUserDTO {

	private String userName;
	private String userAccountKey;
	private String password;
	
	private UserRole role;
	private boolean hasAdmin;
	
	private boolean accountEnabled; 
	
	private LocalDateTime lastActivity;
	private LocalDateTime creationDate;
	
	private List<ApiKey> apiKeys;
	
	
	
	public ApiUserDTO() {}
	public ApiUserDTO(String password, String userName, String userAccountKey, UserRole role, boolean isAdmin,
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
	
}
