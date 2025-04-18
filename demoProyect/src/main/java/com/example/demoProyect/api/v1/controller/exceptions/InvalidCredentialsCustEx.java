package com.example.demoProyect.api.v1.controller.exceptions;

public class InvalidCredentialsCustEx extends CustomException{

	private static final long serialVersionUID = 1L;

	public InvalidCredentialsCustEx() {
		super("The credentials are not valid");
	}

}
