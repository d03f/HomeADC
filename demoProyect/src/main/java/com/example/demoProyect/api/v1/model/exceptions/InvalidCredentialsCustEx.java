package com.example.demoProyect.api.v1.model.exceptions;

public class InvalidCredentialsCustEx extends CustomException{

	private static final long serialVersionUID = 1L;

	public InvalidCredentialsCustEx() {
		super("The credentials are not valid!");
	}

}
