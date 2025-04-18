package com.example.demoProyect.api.v1.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demoProyect.api.v1.model.ApiUser;

@Repository
public interface ApiUserDao {

	
	public Optional<String> getPasswordOfUsername(String searchUsername);
	public boolean isAccountKeyValid(String accountKey);
	public Optional<String> getUserAccountKey(String searchUserName);
	
	public Optional<ApiUser> getApiUserFromAccountKey(String userAcountKey);

}
