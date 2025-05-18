package com.example.demoProyect.api.v1.model.exceptions;

public class AccessDeniedCustEx extends CustomException {

	private static final long serialVersionUID = 1L;

	public AccessDeniedCustEx() {
		super("Your user can't do this action!");
	}
	

}
