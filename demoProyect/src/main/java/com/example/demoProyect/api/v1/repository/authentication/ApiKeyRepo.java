package com.example.demoProyect.api.v1.repository.authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demoProyect.api.v1.model.authentication.ApiKey;
import com.example.demoProyect.api.v1.model.authentication.ApiUser;

@Repository
public interface ApiKeyRepo  extends JpaRepository<ApiKey, String>{
	Optional<ApiKey> findByApiKeyValueAndOwner(String id, ApiUser owner);
	
	@Query("SELECT ak.apiKeyValue FROM ApiKey ak WHERE ak.owner.userAccountKey = :userAccountKey")
	List<String> findApiKeyValuesByOwnerUserAccountKey(@Param("userAccountKey") String userAccountKey);
	
	

	List<ApiKey> findByExpirationDateLessThanEqual(LocalDateTime now);
	Optional<ApiKey> findByApiKeyValueAndKeyEnabledTrue(String apiKeyValue);
	
}
