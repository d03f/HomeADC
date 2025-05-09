package com.example.demoProyect.api.v1.service.data;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demoProyect.api.v1.model.authentication.ApiUser;
import com.example.demoProyect.api.v1.model.data.Sensor;
import com.example.demoProyect.api.v1.model.data.dto.SensorDTO;
import com.example.demoProyect.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.repository.authentication.ApiUserRepo;
import com.example.demoProyect.api.v1.repository.data.SensorRepo;
import com.example.demoProyect.api.v1.service.authentication.users.ApiUserService;

import jakarta.transaction.Transactional;

@Service
public class SensorService {
	private SensorRepo sensorRepo;
	private ApiUserRepo userRepo;
	
	public SensorService(SensorRepo sensorRepo, ApiUserRepo userRepo) {
		this.sensorRepo = sensorRepo;
		this.userRepo = userRepo;
	}
	
	@Transactional
	public List<SensorDTO> getSensorsOfUser(String authorizationHeader) throws InvalidUserAccountKeyCustEx{
		ApiUser apiUser = this.userRepo.findById(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new);
		
		List<Sensor> userSensors = sensorRepo.findByOwner(apiUser);
		
		return userSensors.stream().map(SensorDTO::new).toList();		
	}

}
