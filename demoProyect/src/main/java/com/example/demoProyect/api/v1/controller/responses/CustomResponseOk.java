package com.example.demoProyect.api.v1.controller.responses;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"status", "data"})
public class CustomResponseOk<T> implements CustomResponse {

	private final String status;
	private final T data;
	
	public CustomResponseOk(T data) {
		this.status = "sucess";
		this.data = data;	
	}

	public String getStatus() { return status; }
	public T getData() { return data; }
	
	
	public static <T> ResponseEntity<?> build(T data){
		return ResponseEntity.ok( new CustomResponseOk<T>(data) );
	}
	
}