package com.example.demoProyect.api.v1.model.exceptions;

public class InvalidDataCustEx extends CustomException{
	private static final long serialVersionUID = 1L;

	public InvalidDataCustEx() {
		super("The data is not valid!");
	}

	

}
