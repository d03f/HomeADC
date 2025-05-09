package com.example.demoProyect.api.v1.service.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.model.authentication.ApiUser;
import com.example.demoProyect.api.v1.model.data.DataUnit;
import com.example.demoProyect.api.v1.model.data.Sensor;
import com.example.demoProyect.api.v1.model.data.dto.SensorDTO;
import com.example.demoProyect.api.v1.model.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidDataUnitCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.repository.authentication.ApiUserRepo;
import com.example.demoProyect.api.v1.repository.data.DataUnitRepo;
import com.example.demoProyect.api.v1.repository.data.SensorRepo;
import jakarta.transaction.Transactional;

@Service
public class SensorService {
	private SensorRepo sensorRepo;
	private ApiUserRepo userRepo;
	private DataUnitRepo dataUnitRepo;
	
	private final static String DATA_UNIT_FIELD_NAME = "dataUnit";
	
	public SensorService(SensorRepo sensorRepo, ApiUserRepo userRepo, DataUnitRepo dataUnitRepo) {
		this.sensorRepo = sensorRepo;
		this.userRepo = userRepo;
		this.dataUnitRepo = dataUnitRepo;
	}
	
	@Transactional
	public List<SensorDTO> getSensorsOfUser(String authorizationToken) throws InvalidUserAccountKeyCustEx{
		ApiUser apiUser = this.userRepo.findById(authorizationToken).orElseThrow(InvalidUserAccountKeyCustEx::new);
		
		List<Sensor> userSensors = sensorRepo.findByOwner(apiUser);
		
		return userSensors.stream().map(SensorDTO::new).toList();		
	}

	@Transactional
	public SensorDTO createNewSensor(String authauthorizationToken, Map<String, String> requestData) throws InvalidUserAccountKeyCustEx, InvalidDataUnitCustEx, InvalidDataCustEx {
		ApiUser creator = this.userRepo.findById(authauthorizationToken).orElseThrow(InvalidUserAccountKeyCustEx::new);
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

	public SensorDTO getOneSensorOfUser(String authauthorizationToken, String searchName) throws InvalidUserAccountKeyCustEx, InvalidDataCustEx {
		ApiUser creator = this.userRepo.findById(authauthorizationToken).orElseThrow(InvalidUserAccountKeyCustEx::new);

		return new SensorDTO(
				this.sensorRepo.findByNameAndOwner(searchName, creator).orElseThrow(InvalidDataCustEx::new)
		);
	}

}
