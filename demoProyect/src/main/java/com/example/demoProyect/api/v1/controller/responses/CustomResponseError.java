package com.example.demoProyect.api.v1.controller.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demoProyect.api.v1.model.exceptions.CustomException;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"status", "message"})
public class CustomResponseError implements CustomResponse{
	
	private final String status;
	private final String message;
	
	public CustomResponseError(String message) {
		this.status = "error";
		this.message = message;	
	}

	public String getStatus() { return status; }
	public String getMessage() { return message; }

	public static ResponseEntity<?> build(String message) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new CustomResponseError(message) );
	}
	public static ResponseEntity<?> build(CustomException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new CustomResponseError(e.getMessage()) );
	}
	
}
