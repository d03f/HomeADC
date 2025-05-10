package com.example.demoProyect.api.v1.model.exceptions;

public class InvalidSensorCustEx extends CustomException {

	private static final long serialVersionUID = 1L;

	public InvalidSensorCustEx() { super("The sensor especified is not valid!"); }

}
