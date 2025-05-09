package com.example.demoProyect.api.v1.model.authentication.exceptions;

public abstract class CustomException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CustomException(String msg) {
		super(msg);
	}
}
