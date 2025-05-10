package com.example.demoProyect.api.v1.controller.data;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
