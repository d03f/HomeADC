package com.homeadc.homeadc.api.v1.repository.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.homeadc.homeadc.api.v1.model.authentication.ApiUser;
import com.homeadc.homeadc.api.v1.model.data.Sensor;


@Repository
public interface SensorRepo  extends JpaRepository<Sensor, String>{
	
	 List<Sensor> findByOwner(ApiUser owner);
	 
	 Optional<Sensor> findByName(String name);
	 
	 Optional<Sensor> findByNameAndOwner(String name, ApiUser owner);

	 Optional<Sensor> findByNameAndAllowedApiKeys_ApiKeyValue(String name, String apiKeyValue);
	 
	 List<Sensor> findByAllowedApiKeys_ApiKeyValue(String apiKeyValue);

}
