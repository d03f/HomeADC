package com.homeadc.homeadc.api.v1.model.authentication;

import java.time.LocalDateTime;
import java.util.List;

import com.homeadc.homeadc.api.v1.model.data.Sensor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "apikey")
public class ApiKey {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String apiKeyValue;
	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private ApiUser owner;
	
	@Enumerated(EnumType.STRING)
	private UserRole access;
	
	@Column(nullable = false)
	private boolean keyEnabled;
	
	@Column(nullable = true)
	private LocalDateTime expirationDate;
	
	@Column(nullable = false)
	private LocalDateTime creationDate;
	@Column(nullable = false)
	private LocalDateTime lastActivity;
	
	@ManyToMany(mappedBy = "allowedApiKeys")
    private List<Sensor> accessibleSensors;
	
	
	public ApiKey() {}
	public ApiKey( String name, ApiUser owner, UserRole access, boolean keyEnabled,
			LocalDateTime expirationDate, LocalDateTime creationDate, LocalDateTime lastActivity, List<Sensor> accessibleSensors) {
		
		this.name = name;
		this.owner = owner;
		this.access = access;
		this.keyEnabled = keyEnabled;
		this.expirationDate = expirationDate;
		this.creationDate = creationDate;
		this.lastActivity = lastActivity;
		this.accessibleSensors = accessibleSensors;
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


	public LocalDateTime getExpirationDate() { return expirationDate; }
	public void setExpirationDate(LocalDateTime expirationDate) { this.expirationDate = expirationDate; }


	public LocalDateTime getCreationDate() { return creationDate; }
	public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }


	public LocalDateTime getLastActivity() { return lastActivity; }
	public void setLastActivity(LocalDateTime lastActivity) { this.lastActivity = lastActivity; }
	
	
	public List<Sensor> getAccessibleSensors() { return accessibleSensors; }
	public void setAccessibleSensors(List<Sensor> accessibleSensors) { this.accessibleSensors = accessibleSensors; }
	
	

}
