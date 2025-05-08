package com.example.demoProyect.api.v1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demoProyect.api.v1.model.ApiKey;

@Repository
public interface ApiKeyRepo  extends JpaRepository<ApiKey, String>{
	@Query("SELECT ak.apiKeyValue FROM ApiKey ak WHERE ak.owner.userAccountKey = :userAccountKey")
	List<String> findApiKeyValuesByOwnerUserAccountKey(@Param("userAccountKey") String userAccountKey);
	
}
