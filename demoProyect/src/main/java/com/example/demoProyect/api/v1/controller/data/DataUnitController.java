package com.example.demoProyect.api.v1.controller.data;

import java.util.Map;

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
import com.example.demoProyect.api.v1.model.exceptions.InvalidDataCustEx;
import com.example.demoProyect.api.v1.model.exceptions.InvalidUserAccountKeyCustEx;
import com.example.demoProyect.api.v1.service.RequestDataParserService;
import com.example.demoProyect.api.v1.service.data.DataUnitService;

@RestController
@RequestMapping("/api/v1/dataunits")
public class DataUnitController {
	
	private DataUnitService dataUnitService;
	private RequestDataParserService dataParser;
	
	public DataUnitController(DataUnitService dataUnitService, RequestDataParserService dataParser) {
		this.dataUnitService = dataUnitService;
		this.dataParser = dataParser;
	}
	
	
	@GetMapping
	public ResponseEntity<?> getAllDataUnits(){
		return CustomResponseOk.build( this.dataUnitService.getAllDataUnits() );
	}
	
	@PostMapping
	public ResponseEntity<?> createNewDataUnit(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody Map<String, String> requestData){
		try {
			return CustomResponseOk.build(  
					this.dataUnitService.createNewDataUnit(
							dataParser.parseAccountKeyFromHeader(authorizationHeader).orElseThrow(InvalidUserAccountKeyCustEx::new),
							requestData) 
					);
			
		} catch (InvalidUserAccountKeyCustEx | InvalidDataCustEx | DuplicatedEntryCustEx | AccessDeniedCustEx e) { return CustomResponseError.build( e.getMessage()); } 
	}

}
