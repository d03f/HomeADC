package com.homeadc.homeadc.api.v1.service.data;

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

import com.homeadc.homeadc.api.v1.model.authentication.ApiKey;
import com.homeadc.homeadc.api.v1.model.data.Sensor;
import com.homeadc.homeadc.api.v1.model.data.SensorRecord;
import com.homeadc.homeadc.api.v1.model.data.dto.SensorRecordDTO;
import com.homeadc.homeadc.api.v1.model.exceptions.AccessDeniedCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidSensorCustEx;
import com.homeadc.homeadc.api.v1.repository.authentication.ApiKeyRepo;
import com.homeadc.homeadc.api.v1.repository.data.SensorRecordRepo;
import com.homeadc.homeadc.api.v1.repository.data.SensorRepo;
import com.homeadc.homeadc.api.v1.repository.specifications.SensorRecordSpecifications;
import com.homeadc.homeadc.api.v1.service.authentication.UserRoleManager;

import jakarta.transaction.Transactional;

@Service
public class SensorRecordService {
	
	private SensorRecordRepo recordRepo;
	private ApiKeyRepo apiRepo;
	private SensorRepo sensorRepo;
	private UserRoleManager accessService;
	
	public SensorRecordService(SensorRecordRepo recordRepo, ApiKeyRepo apiRepo, SensorRepo sensorRepo, UserRoleManager accessService) {
		this.recordRepo = recordRepo; 
		this.apiRepo = apiRepo;
		this.sensorRepo = sensorRepo;
		this.accessService = accessService;
	}
	
	@Transactional
	public List<SensorRecordDTO> getSensorRecord(
			String apiKey, String sensorName,
			Optional<String> minValue, Optional<String> maxValue,
			Optional<String> startDate, Optional<String> endDate,
			Optional<String> metadataContains, 
			Pageable pageable ) throws InvalidApiKeyCustEx, InvalidSensorCustEx, AccessDeniedCustEx{
		ApiKey usedApikey = this.apiRepo.findByApiKeyValueAndKeyEnabledTrue(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		accessService.canKeyRead(usedApikey);
		
		this.sensorRepo.findByNameAndAllowedApiKeys_ApiKeyValue(sensorName, usedApikey.getApiKeyValue()).orElseThrow(InvalidSensorCustEx::new);
		
		
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
	public SensorRecordDTO postSensorRecord(String apiKey, String sensorName, @RequestBody Map<String, String> requestData) throws InvalidApiKeyCustEx, InvalidSensorCustEx, AccessDeniedCustEx {
		ApiKey sender = this.apiRepo.findByApiKeyValueAndKeyEnabledTrue(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		accessService.canKeyWrite(sender);
		
		
		Sensor usedSensor = this.sensorRepo.findByNameAndAllowedApiKeys_ApiKeyValue(sensorName, apiKey).orElseThrow(InvalidSensorCustEx::new);
		
		SensorRecord sensorRecord = new SensorRecord();
		
		sensorRecord.setValue(new BigDecimal( requestData.getOrDefault("value", null) ));
		sensorRecord.setMetadata(requestData.getOrDefault("metadata", null));
		
		sensorRecord.setSensor(usedSensor);
		sensorRecord.setTimestamp( LocalDateTime.now() );
		
		this.recordRepo.save(sensorRecord);
		
		return new SensorRecordDTO(sensorRecord);
	}

	public SensorRecordDTO getMaxMinSensorRecord(String apiKey, String sensorName, String option) throws InvalidApiKeyCustEx, InvalidSensorCustEx, NullPointerException, AccessDeniedCustEx {
		ApiKey sended = this.apiRepo.findByApiKeyValueAndKeyEnabledTrue(apiKey).orElseThrow(InvalidApiKeyCustEx::new);
		accessService.canKeyRead(sended);
		
		
		this.sensorRepo.findByName(sensorName).orElseThrow(InvalidSensorCustEx::new);
		this.sensorRepo.findByNameAndAllowedApiKeys_ApiKeyValue(sensorName, apiKey).orElseThrow(InvalidSensorCustEx::new);
		
		SensorRecordDTO returnData = new SensorRecordDTO();
		returnData.setTimestamp(LocalDateTime.now());
		
		if (option.equals("max")) {
			returnData = new SensorRecordDTO( this.recordRepo.findTopBySensor_NameOrderByValueDesc(sensorName).orElseGet(null) );
			returnData.setMetadata("This is the maximun value of all records");
			
		} else if (option.equals("min")) {
			returnData = new SensorRecordDTO( this.recordRepo.findTopBySensor_NameOrderByValueAsc(sensorName).orElseGet(null) );
			returnData.setMetadata("This is the minimum value of all records");
			
			
		} else if (option.equals("avg")) {
			returnData.setValue(  this.recordRepo.findAverageValueBySensorName(sensorName).orElseGet(null)  );
			returnData.setMetadata("This is the average value of all the records");
		}
		
		return returnData;
	}

}
