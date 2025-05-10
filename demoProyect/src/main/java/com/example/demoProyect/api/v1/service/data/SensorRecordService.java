package com.example.demoProyect.api.v1.service.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demoProyect.api.v1.model.data.Sensor;
import com.example.demoProyect.api.v1.model.data.SensorRecord;
import com.example.demoProyect.api.v1.model.data.dto.SensorRecordDTO;
import com.example.demoProyect.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidSensorCustEx;
import com.example.demoProyect.api.v1.repository.authentication.ApiKeyRepo;
import com.example.demoProyect.api.v1.repository.data.SensorRecordRepo;
import com.example.demoProyect.api.v1.repository.data.SensorRepo;

import jakarta.transaction.Transactional;

@Service
public class SensorRecordService {
	
	private SensorRecordRepo recordRepo;
	private ApiKeyRepo apiRepo;
	private SensorRepo sensorRepo;
	
	public SensorRecordService(SensorRecordRepo recordRepo, ApiKeyRepo apiRepo, SensorRepo sensorRepo) {
		this.recordRepo = recordRepo; 
		this.apiRepo = apiRepo;
		this.sensorRepo = sensorRepo;
	}
	
	@Transactional
	public SensorRecordDTO postSensorRecord(String apiKey, String sensorName, @RequestBody Map<String, String> requestData) throws InvalidApiKeyCustEx, InvalidSensorCustEx {
		this.apiRepo.findByApiKeyValueAndKeyEnabledTrue(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		
		this.sensorRepo.findByName(sensorName).orElseThrow(InvalidSensorCustEx::new);
		
		Sensor usedSensor = this.sensorRepo.findByNameAndAllowedApiKeys_ApiKeyValue(sensorName, apiKey).orElseThrow(InvalidSensorCustEx::new);
		
		SensorRecord sensorRecord = new SensorRecord();
		
		sensorRecord.setValue(new BigDecimal( requestData.getOrDefault("value", null) ));
		sensorRecord.setMetadata(requestData.getOrDefault("medatata", null));
		
		sensorRecord.setSensor(usedSensor);
		sensorRecord.setTimestamp( LocalDateTime.now() );
		
		this.recordRepo.save(sensorRecord);
		
		return new SensorRecordDTO(sensorRecord);
	}

}
