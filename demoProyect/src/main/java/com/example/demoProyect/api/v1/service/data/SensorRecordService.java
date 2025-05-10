package com.example.demoProyect.api.v1.service.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
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
import com.example.demoProyect.api.v1.repository.specifications.SensorRecordSpecifications;

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
	public List<SensorRecordDTO> getSensorRecord(
			String apiKey, String sensorName,
			Optional<String> minValue, Optional<String> maxValue,
			Optional<String> startDate, Optional<String> endDate,
			Optional<String> metadataContains, 
			Pageable pageable ) throws InvalidApiKeyCustEx, InvalidSensorCustEx{
		this.apiRepo.findByApiKeyValueAndKeyEnabledTrue(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		this.sensorRepo.findByName(sensorName).orElseThrow(InvalidSensorCustEx::new);
		
		
		Specification<SensorRecord> spec = this.createFilters(sensorName, minValue, maxValue, startDate, endDate, metadataContains);
		
		Page<SensorRecord> resultPage = this.recordRepo.findAll(spec, pageable);
		return resultPage.getContent().stream().map(SensorRecordDTO::new).toList();
	}
	

	private Specification<SensorRecord> createFilters( String sensorName,
			Optional<String> minValue, Optional<String> maxValue,
			Optional<String> startDate, Optional<String> endDate,
			Optional<String> metadataContains){
		Specification<SensorRecord> spec = SensorRecordSpecifications.byName(sensorName);
		
		if (minValue.isPresent()) {
			spec = spec.and(  SensorRecordSpecifications.byValueGreaterThanOrEqualTo(new BigDecimal( minValue.get() ))  );
		}
		if (maxValue.isPresent()) {
			spec = spec.and(  SensorRecordSpecifications.byValueLessThanOrEqualTo(new BigDecimal( maxValue.get() ) )   );
		}
		
		if (startDate.isPresent()) {
			spec = spec.and(   SensorRecordSpecifications.byTimestampGreaterThanOrEqualTo(LocalDateTime.parse( startDate.get() ) )   );
		}
		if (endDate.isPresent()) {
			spec = spec.and(   SensorRecordSpecifications.byTimestampLessThanOrEqualTo(LocalDateTime.parse( endDate.get() ) )   );
		}
		
		if (metadataContains.isPresent()) {
			spec = spec.and(   SensorRecordSpecifications.byMetadataContains( metadataContains.get() )   );
		}
		
		return spec;
		
	}
	
	
	@Transactional
	public SensorRecordDTO postSensorRecord(String apiKey, String sensorName, @RequestBody Map<String, String> requestData) throws InvalidApiKeyCustEx, InvalidSensorCustEx {
		this.apiRepo.findByApiKeyValueAndKeyEnabledTrue(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		
		this.sensorRepo.findByName(sensorName).orElseThrow(InvalidSensorCustEx::new);
		
		Sensor usedSensor = this.sensorRepo.findByNameAndAllowedApiKeys_ApiKeyValue(sensorName, apiKey).orElseThrow(InvalidSensorCustEx::new);
		
		SensorRecord sensorRecord = new SensorRecord();
		
		sensorRecord.setValue(new BigDecimal( requestData.getOrDefault("value", null) ));
		sensorRecord.setMetadata(requestData.getOrDefault("metadata", null));
		
		sensorRecord.setSensor(usedSensor);
		sensorRecord.setTimestamp( LocalDateTime.now() );
		
		this.recordRepo.save(sensorRecord);
		
		return new SensorRecordDTO(sensorRecord);
	}

}
