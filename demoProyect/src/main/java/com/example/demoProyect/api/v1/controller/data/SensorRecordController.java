package com.example.demoProyect.api.v1.controller.data;

import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoProyect.api.v1.controller.responses.CustomResponseError;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseOk;
import com.example.demoProyect.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidSensorCustEx;
import com.example.demoProyect.api.v1.service.RequestDataParserService;
import com.example.demoProyect.api.v1.service.data.SensorRecordService;

@RestController
@RequestMapping("/api/v1/records")
public class SensorRecordController {
	
	private SensorRecordService recordService;
	private RequestDataParserService dataParser;
	
	public SensorRecordController(SensorRecordService recordService, RequestDataParserService dataParser) {
		this.recordService = recordService;
		this.dataParser = dataParser;
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<?> getRecords(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
			@PathVariable String name,
			@RequestParam(required = false) Optional<String> minValue,
			@RequestParam(required = false) Optional<String> maxValue,
			
			@RequestParam(required = false) Optional<String> startDate,
			@RequestParam(required = false) Optional<String> endDate,
			
			@RequestParam(required = false) Optional<String> metadataContains,
			@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable
			) {
		try {
			return CustomResponseOk.build(
					this.recordService.getSensorRecord(
					this.dataParser.parseApiKeyFromHeader(authorizationHeader).orElseThrow(InvalidApiKeyCustEx::new), name,
					minValue, maxValue, startDate, endDate, metadataContains, pageable)
				);
		} catch (InvalidApiKeyCustEx | InvalidSensorCustEx e) { return CustomResponseError.build(e); }
		
	}
	
	@GetMapping("/{name}/{option}")
	public ResponseEntity<?> getRecords(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
			@PathVariable String name, @PathVariable String option) {
		try {
			return CustomResponseOk.build(
					this.recordService.getMaxMinSensorRecord(
							this.dataParser.parseApiKeyFromHeader(authorizationHeader).orElseThrow(InvalidApiKeyCustEx::new), name, option)
				);
		} catch (InvalidApiKeyCustEx | InvalidSensorCustEx  e) { return CustomResponseError.build(e); }
		catch (NullPointerException e) { return CustomResponseError.build(e.getMessage()); } 
		
	}
	
	@PostMapping("/{name}")
	public ResponseEntity<?> postRecordOfSensor( 
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
			@PathVariable String name, @RequestBody Map<String, String> requestData){
		
		try {
			return CustomResponseOk.build(
				this.recordService.postSensorRecord(
						this.dataParser.parseApiKeyFromHeader(authorizationHeader).orElseThrow(InvalidApiKeyCustEx::new), 
						name, requestData)
				);
		} 
		catch (InvalidApiKeyCustEx | InvalidSensorCustEx e) { return CustomResponseError.build(e); }
		catch (NullPointerException | NumberFormatException e) { return CustomResponseError.build(e.getMessage()); }
				
	}

}
