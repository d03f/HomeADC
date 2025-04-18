package com.example.demoProyect.api.v1.controller.exceptions;

public abstract class CustomException extends Exception {

	private static final long serialVersionUID = 1L;
	
	protected CustomException(String msg) {
		super(msg);
	}

}
