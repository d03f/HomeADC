package com.example.demoProyect.api.v1.controller.exceptions;

public class AccessDeniedCustEx extends CustomException {

	private static final long serialVersionUID = 1L;

	public AccessDeniedCustEx() {
		super("Your user cant do this action!");
	}
	

}
