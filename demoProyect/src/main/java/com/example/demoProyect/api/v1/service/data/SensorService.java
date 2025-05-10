package com.example.demoProyect.api.v1.service.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.model.authentication.ApiKey;
import com.example.demoProyect.api.v1.model.authentication.ApiUser;
import com.example.demoProyect.api.v1.model.data.DataUnit;
import com.example.demoProyect.api.v1.model.data.Sensor;
import com.example.demoProyect.api.v1.model.data.dto.SensorDTO;
import com.example.demoProyect.api.v1.model.exceptions.AccessDeniedCustEx;
import com.example.demoProyect.api.v1.model.exceptions.DuplicatedEntryCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidDataUnitCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.repository.authentication.ApiKeyRepo;
import com.example.demoProyect.api.v1.repository.authentication.ApiUserRepo;
import com.example.demoProyect.api.v1.repository.data.DataUnitRepo;
import com.example.demoProyect.api.v1.repository.data.SensorRepo;
import jakarta.transaction.Transactional;

@Service
public class SensorService {
	private SensorRepo sensorRepo;
	private ApiUserRepo userRepo;
	private DataUnitRepo dataUnitRepo;
	private ApiKeyRepo apiKeyRepo;
	
	private static final String DATA_UNIT_FIELD_NAME = "dataUnit";
	
	public SensorService(SensorRepo sensorRepo, ApiUserRepo userRepo, DataUnitRepo dataUnitRepo, ApiKeyRepo apiKeyRepo) {
		this.sensorRepo = sensorRepo;
		this.userRepo = userRepo;
		this.dataUnitRepo = dataUnitRepo;
		this.apiKeyRepo = apiKeyRepo;
	}
	
	@Transactional
	public List<SensorDTO> getSensorsOfUser(String authorizationToken) throws InvalidUserAccountKeyCustEx{
		ApiUser apiUser = this.userRepo.findById(authorizationToken).orElseThrow(InvalidUserAccountKeyCustEx::new);
		
		List<Sensor> userSensors = sensorRepo.findByOwner(apiUser);
		
		return userSensors.stream().map(SensorDTO::new).toList();		
	}

	@Transactional
	public SensorDTO createNewSensor(String authauthorizationToken, Map<String, String> requestData) throws InvalidUserAccountKeyCustEx, InvalidDataUnitCustEx, InvalidDataCustEx, DuplicatedEntryCustEx {
		ApiUser creator = this.userRepo.findById(authauthorizationToken).orElseThrow(InvalidUserAccountKeyCustEx::new);
		
		if (this.sensorRepo.findByNameAndOwner(requestData.get("name"), creator).isPresent()) { throw new DuplicatedEntryCustEx(); }
		
		Sensor createdSensor = new Sensor();
		
		createdSensor.setOwner(creator);
		
		
		String name = requestData.get("name"); createdSensor.setName(name); if(name == null) { throw new InvalidDataCustEx(); }
		createdSensor.setDescription(requestData.get("description"));
		createdSensor.setLocation(requestData.get("location"));
		
		createdSensor.setCreationDate(LocalDateTime.now());
		createdSensor.setLastActivity(LocalDateTime.now());
		
		DataUnit choosenDataUnit = this.dataUnitRepo.findBySymbolOrName(requestData.get(DATA_UNIT_FIELD_NAME), requestData.get(DATA_UNIT_FIELD_NAME))
				.orElseThrow(InvalidDataUnitCustEx::new);
		
		createdSensor.setDataUnit(choosenDataUnit);
		
		
		return new SensorDTO( this.sensorRepo.save(createdSensor) );
		
	}
	
	@Transactional
	public SensorDTO removeSensor(String authauthorizationToken,String name) throws InvalidUserAccountKeyCustEx, InvalidDataUnitCustEx, InvalidDataCustEx, DuplicatedEntryCustEx {
		ApiUser creator = this.userRepo.findById(authauthorizationToken).orElseThrow(InvalidUserAccountKeyCustEx::new);
		Sensor toBeDeleted = this.sensorRepo.findByNameAndOwner(name, creator).orElseThrow(InvalidDataCustEx::new);
		
		this.sensorRepo.delete(toBeDeleted);
		
		return new SensorDTO( toBeDeleted );
	
	}
	@Transactional
	public SensorDTO getOneSensorOfUser(String authorizationToken, String searchName) throws InvalidUserAccountKeyCustEx, InvalidDataCustEx {
		ApiUser creator = this.userRepo.findById(authorizationToken).orElseThrow(InvalidUserAccountKeyCustEx::new);

		return new SensorDTO(
				this.sensorRepo.findByNameAndOwner(searchName, creator).orElseThrow(InvalidDataCustEx::new)
		);
	}
	
	@Transactional
	public SensorDTO addNewApiKeyToSensor(String authorizationToken, String apiKeyToAdd, String nameOfSensor) 
			throws InvalidUserAccountKeyCustEx, AccessDeniedCustEx, InvalidApiKeyCustEx, DuplicatedEntryCustEx {
		
		ApiUser ownerAndUser = this.userRepo.findById(authorizationToken).orElseThrow(InvalidUserAccountKeyCustEx::new);
		Sensor toUpdateSensor = this.sensorRepo.findByNameAndOwner(nameOfSensor, ownerAndUser).orElseThrow(AccessDeniedCustEx::new);
		
		ApiKey apiKey = this.apiKeyRepo.findById(apiKeyToAdd).orElseThrow(InvalidApiKeyCustEx::new);
		
		if (toUpdateSensor.containsAllowedApikey(apiKey)) { throw new DuplicatedEntryCustEx(); }
		
		toUpdateSensor.addAllowedApiKey(apiKey);
		this.sensorRepo.save(toUpdateSensor);
		
		return new SensorDTO(toUpdateSensor);
	}
	
	@Transactional
	public SensorDTO addUsedApiKeyToSensor(String apiKeyValue, String nameOfSensor) 
			throws AccessDeniedCustEx, InvalidApiKeyCustEx, DuplicatedEntryCustEx {
		ApiKey apiKey = this.apiKeyRepo.findByApiKeyValueAndKeyEnabledTrue(apiKeyValue).orElseThrow(InvalidApiKeyCustEx::new);
		ApiUser owner = apiKey.getOwner();
		Sensor toUpdateSensor = this.sensorRepo.findByNameAndOwner(nameOfSensor, owner).orElseThrow(AccessDeniedCustEx::new);
		
		if (toUpdateSensor.containsAllowedApikey(apiKey)) { throw new DuplicatedEntryCustEx(); }
		
		toUpdateSensor.addAllowedApiKey(apiKey);
		this.sensorRepo.save(toUpdateSensor);
		
		return new SensorDTO(toUpdateSensor);
	}
	
	
	@Transactional
	public SensorDTO removeNewApiKeyToSensor(String authorizationToken, String apiKeyToAdd, String nameOfSensor) 
			throws InvalidUserAccountKeyCustEx, AccessDeniedCustEx, InvalidApiKeyCustEx, InvalidDataCustEx {
		
		ApiUser ownerAndUser = this.userRepo.findById(authorizationToken).orElseThrow(InvalidUserAccountKeyCustEx::new);
		Sensor toUpdateSensor = this.sensorRepo.findByNameAndOwner(nameOfSensor, ownerAndUser).orElseThrow(AccessDeniedCustEx::new);
		
		ApiKey apiKey = this.apiKeyRepo.findById(apiKeyToAdd).orElseThrow(InvalidApiKeyCustEx::new);
		
		if (!toUpdateSensor.containsAllowedApikey(apiKey)) { throw new InvalidDataCustEx(); }
		
		toUpdateSensor.removeAllowedApiKey(apiKey);
		this.sensorRepo.save(toUpdateSensor);
		
		return new SensorDTO(toUpdateSensor);
	}
	
	@Transactional
	public SensorDTO removeUsedApiKeyToSensor(String apiKeyValue, String nameOfSensor) 
			throws AccessDeniedCustEx, InvalidApiKeyCustEx, InvalidDataCustEx {
		ApiKey apiKey = this.apiKeyRepo.findByApiKeyValueAndKeyEnabledTrue(apiKeyValue).orElseThrow(InvalidApiKeyCustEx::new);
		ApiUser owner = apiKey.getOwner();
		Sensor toUpdateSensor = this.sensorRepo.findByNameAndOwner(nameOfSensor, owner).orElseThrow(AccessDeniedCustEx::new);
		
		if (!toUpdateSensor.containsAllowedApikey(apiKey)) { throw new InvalidDataCustEx(); }
		
		toUpdateSensor.removeAllowedApiKey(apiKey);
		this.sensorRepo.save(toUpdateSensor);
		
		return new SensorDTO(toUpdateSensor);
	}

}
