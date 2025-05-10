package com.example.demoProyect.api.v1.model.exceptions;

public class InvalidDataUnitCustEx extends CustomException{

	private static final long serialVersionUID = 1L;

	public InvalidDataUnitCustEx() {
		super("The dataUnit type is not valid!");
	}
	

}
