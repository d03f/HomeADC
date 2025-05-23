package com.homeadc.homeadc.api.v1.model.authentication;

import java.time.LocalDateTime;
import java.util.List;

import com.homeadc.homeadc.api.v1.model.data.Sensor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "apiuser")
public class ApiUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String userAccountKey;

	@Column(nullable = false, unique = true)
	private String userName;

	@Column(nullable = false)
	private String password;
	
	private UserRole role;
	@Column(nullable = false)
	private boolean hasAdmin;
	@Column(nullable = false)
	private boolean accountEnabled; 
	
	@Column(nullable = false)
	private LocalDateTime lastActivity;
	@Column(nullable = false)
	private LocalDateTime creationDate;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<ApiKey> apiKeys;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Use CascadeType.ALL cautiously
    private List<Sensor> ownedSensors;
	
	
	
	public ApiUser() {}
	public ApiUser(String password, String userName, UserRole role, boolean isAdmin,
			boolean accountEnabled, LocalDateTime lastActivity, LocalDateTime creationDate, List<ApiKey> apiKeys, List<Sensor> ownedSensors) {
		super();
		this.password = password;
		this.userName = userName;
		this.role = role;
		this.hasAdmin = isAdmin;
		this.accountEnabled = accountEnabled;
		this.lastActivity = lastActivity;
		this.creationDate = creationDate;
		this.apiKeys = apiKeys;
		this.ownedSensors = ownedSensors;
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
	
	public List<Sensor> getOwnedSensors() { return ownedSensors; }
	public void setOwnedSensors(List<Sensor> ownedSensors) { this.ownedSensors = ownedSensors; }

	
	
	@Override
    public String toString() {
        // Use null-safe checks and EXCLUDE SENSITIVE DATA (like password)
        return "ApiUser{" +
               "userAccountKey='" + userAccountKey + '\'' +
               ", userName='" + userName + '\'' +
               // !!! DO NOT INCLUDE PASSWORD FIELD !!!
               ", role=" + role + // Assuming UserRole's toString is safe
               ", hasAdmin=" + hasAdmin +
               ", accountEnabled=" + accountEnabled +
               ", lastActivity=" + lastActivity +
               ", creationDate=" + creationDate +
               // Print count of relationships to avoid recursion and lazy loading in simple logging
               ", apiKeysCount=" + (apiKeys != null ? apiKeys.size() : 0) +
               ", ownedSensorsCount=" + (ownedSensors != null ? ownedSensors.size() : 0) +
               '}';
    }
	
}

