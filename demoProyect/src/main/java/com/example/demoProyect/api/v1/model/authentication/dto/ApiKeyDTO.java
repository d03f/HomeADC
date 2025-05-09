package com.example.demoProyect.api.v1.model.authentication.dto;

import java.time.LocalDateTime;

import com.example.demoProyect.api.v1.model.authentication.ApiKey;
import com.example.demoProyect.api.v1.model.authentication.ApiUser;
import com.example.demoProyect.api.v1.model.authentication.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"name", "apiKeyValue", "access", "keyEnabled", "expirationDate", "creationDate", "lastActivity", "owner"})
public class ApiKeyDTO {
	
	private String apiKeyValue;
	private String name;
	@JsonIgnore
	private ApiUser owner;
	
	private UserRole access;
	private boolean keyEnabled;
	
	private LocalDateTime expirationDate;
	
	private LocalDateTime creationDate;
	private LocalDateTime lastActivity;
	
	
	public ApiKeyDTO() {}
	public ApiKeyDTO(ApiKey apiKeyData) {
		this.apiKeyValue = apiKeyData.getApiKeyValue();
		this.name = apiKeyData.getName();
		this.owner =  apiKeyData.getOwner();
		this.access =  apiKeyData.getAccess();
		this.keyEnabled =  apiKeyData.isKeyEnabled();
		this.expirationDate =  apiKeyData.getExpirationDate();
		this.creationDate =  apiKeyData.getCreationDate();
		this.lastActivity =  apiKeyData.getLastActivity();
		
	}
	
	
	
	public String getApiKeyValue() {  return apiKeyValue; }
	public void setApiKeyValue(String apiKeyValue) {  this.apiKeyValue = apiKeyValue; }
	
	public String getName() {  return name; }
	public void setName(String name) {  this.name = name; }
	public ApiUser getOwner() {  return owner; }
	public void setOwner(ApiUser owner) {  this.owner = owner; }
	
	public UserRole getAccess() {  return access; }
	public void setAccess(UserRole access) {  this.access = access; }
	
	public boolean isKeyEnabled() {  return keyEnabled; }
	public void setKeyEnabled(boolean keyEnabled) { this.keyEnabled = keyEnabled; }
	
	public LocalDateTime getExpirationDate() {  return expirationDate; }
	public void setExpirationDate(LocalDateTime expirationDate) {  this.expirationDate = expirationDate; }
	
	public LocalDateTime getCreationDate() {  return creationDate; }
	public void setCreationDate(LocalDateTime creationDate) {  this.creationDate = creationDate; }
	
	public LocalDateTime getLastActivity() {  return lastActivity; }
	public void setLastActivity(LocalDateTime lastActivity) {  this.lastActivity = lastActivity; }
}
