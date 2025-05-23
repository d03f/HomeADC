package com.homeadc.homeadc.api.v1.controller.data;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeadc.homeadc.api.v1.controller.responses.CustomResponseError;
import com.homeadc.homeadc.api.v1.controller.responses.CustomResponseOk;
import com.homeadc.homeadc.api.v1.model.exceptions.AccessDeniedCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.DuplicatedEntryCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidApiKeyCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidDataCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidDataUnitCustEx;
import com.homeadc.homeadc.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.homeadc.homeadc.api.v1.service.RequestDataParserService;
import com.homeadc.homeadc.api.v1.service.data.SensorService;

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
		} catch ( InvalidUserAccountKeyCustEx | AccessDeniedCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	@GetMapping("/me/{value}")
	public ResponseEntity<?> getOneSensorOfUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable String value){
		try {
			return CustomResponseOk.build( 
					this.sensorService.getOneSensorOfUser(
							parserService.parseAccountKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new), value
						));
		} catch ( InvalidUserAccountKeyCustEx | InvalidDataCustEx | AccessDeniedCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	
	@PostMapping("/me")
	public ResponseEntity<?> createNewSensor(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData){
		try {
			return CustomResponseOk.build( 
					this.sensorService.createNewSensor(
							parserService.parseAccountKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new), requestData
						));
		} catch ( InvalidUserAccountKeyCustEx | InvalidDataUnitCustEx | InvalidDataCustEx | DuplicatedEntryCustEx | AccessDeniedCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	
	@DeleteMapping("/me/{value}")
	public ResponseEntity<?> removeSensor(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable String value){
		try {
			return CustomResponseOk.build( 
					this.sensorService.removeSensor(
							parserService.parseAccountKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new), value
						));
		} catch ( InvalidUserAccountKeyCustEx | InvalidDataUnitCustEx | InvalidDataCustEx | DuplicatedEntryCustEx | AccessDeniedCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	@PostMapping("/me/{name}/keys")
	public ResponseEntity<?> addApiKeyToSensor(@RequestHeader( required = false, value = "Authorization" ) String authorizationHeader,
			@PathVariable String name,
			@RequestBody Map<String, String> requestData ){
		Optional<String> accountKey = parserService.parseAccountKeyFromHeader(authorizationHeader);
		
		try {
			Object toSend;
			if (accountKey.isPresent()) { toSend = this.sensorService.addNewApiKeyToSensor(accountKey.get(), requestData.get("apiKeyValue"), name); }
			else {  toSend = this.sensorService.addUsedApiKeyToSensor(requestData.get("apiKeyValue"), requestData.get("name")); }
		
			return CustomResponseOk.build(toSend);
		} catch (InvalidUserAccountKeyCustEx | AccessDeniedCustEx | InvalidApiKeyCustEx | DuplicatedEntryCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	@DeleteMapping("/me/{name}/keys/{apikey}")
	public ResponseEntity<?> removeApiKeyToSensor(@RequestHeader( required = false, value = "Authorization" ) String authorizationHeader, 
			@PathVariable("name") String name, @PathVariable("apikey") String apiKey){
		Optional<String> accountKey = parserService.parseAccountKeyFromHeader(authorizationHeader);
		
		try {
			Object toSend;
			if (accountKey.isPresent()) { toSend = this.sensorService.removeNewApiKeyToSensor(accountKey.get(), apiKey, name); }
			else {  toSend = this.sensorService.removeUsedApiKeyToSensor(apiKey, name); }
		
			return CustomResponseOk.build(toSend);
		} catch (InvalidUserAccountKeyCustEx | AccessDeniedCustEx | InvalidApiKeyCustEx | InvalidDataCustEx e) {
			return CustomResponseError.build( e.getMessage() );
		}
	}
	
	
}
