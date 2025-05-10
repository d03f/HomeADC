package com.example.demoProyect.api.v1.controller.data;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoProyect.api.v1.controller.responses.CustomResponseError;
import com.example.demoProyect.api.v1.controller.responses.CustomResponseOk;
import com.example.demoProyect.api.v1.model.exceptions.AccessDeniedCustEx;
import com.example.demoProyect.api.v1.model.exceptions.DuplicatedEntryCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidDataUnitCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.service.RequestDataParserService;
import com.example.demoProyect.api.v1.service.data.SensorService;

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorController {
	
	private RequestDataParserService parserService;
	private SensorService sensorService;
	
	public SensorController(RequestDataParserService parserService, SensorService sensorService) {
		this.parserService = parserService;
		this.sensorService = sensorService;
	}
	

	@GetMapping("/me")
	public ResponseEntity<?> getSensorsOfUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
		try {
			return CustomResponseOk.build( 
					this.sensorService.getSensorsOfUser(
							parserService.parseAccountKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new)
						));
		} catch ( InvalidUserAccountKeyCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	@GetMapping("/me/info")
	public ResponseEntity<?> getOneSensorOfUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData){
		try {
			return CustomResponseOk.build( 
					this.sensorService.getOneSensorOfUser(
							parserService.parseAccountKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new), requestData.get("name")
						));
		} catch ( InvalidUserAccountKeyCustEx | InvalidDataCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	
	@PostMapping("/me/generate")
	public ResponseEntity<?> createNewSensor(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData){
		try {
			return CustomResponseOk.build( 
					this.sensorService.createNewSensor(
							parserService.parseAccountKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new), requestData
						));
		} catch ( InvalidUserAccountKeyCustEx | InvalidDataUnitCustEx | InvalidDataCustEx | DuplicatedEntryCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	
	@PostMapping("/me/remove")
	public ResponseEntity<?> removeSensor(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData){
		try {
			return CustomResponseOk.build( 
					this.sensorService.removeSensor(
							parserService.parseAccountKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new), requestData
						));
		} catch ( InvalidUserAccountKeyCustEx | InvalidDataUnitCustEx | InvalidDataCustEx | DuplicatedEntryCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	@PostMapping("/me/addkey")
	public ResponseEntity<?> addApiKeyToSensor(@RequestHeader( required = false, value = "Authorization" ) String authorizationHeader, @RequestBody Map<String, String> requestData ){
		Optional<String> accountKey = parserService.parseAccountKeyFromHeader(authorizationHeader);
		
		try {
			Object toSend;
			if (accountKey.isPresent()) { toSend = this.sensorService.addNewApiKeyToSensor(accountKey.get(), requestData.get("apiKeyValue"), requestData.get("name")); }
			else {  toSend = this.sensorService.addUsedApiKeyToSensor(requestData.get("apiKeyValue"), requestData.get("name")); }
		
			return CustomResponseOk.build(toSend);
		} catch (InvalidUserAccountKeyCustEx | AccessDeniedCustEx | InvalidApiKeyCustEx | DuplicatedEntryCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	@PostMapping("/me/removekey")
	public ResponseEntity<?> removeApiKeyToSensor(@RequestHeader( required = false, value = "Authorization" ) String authorizationHeader, @RequestBody Map<String, String> requestData ){
		Optional<String> accountKey = parserService.parseAccountKeyFromHeader(authorizationHeader);
		
		try {
			Object toSend;
			if (accountKey.isPresent()) { toSend = this.sensorService.removeNewApiKeyToSensor(accountKey.get(), requestData.get("apiKeyValue"), requestData.get("name")); }
			else {  toSend = this.sensorService.removeUsedApiKeyToSensor(requestData.get("apiKeyValue"), requestData.get("name")); }
		
			return CustomResponseOk.build(toSend);
		} catch (InvalidUserAccountKeyCustEx | AccessDeniedCustEx | InvalidApiKeyCustEx | InvalidDataCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	
}
