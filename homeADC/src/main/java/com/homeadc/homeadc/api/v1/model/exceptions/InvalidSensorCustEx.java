package com.homeadc.homeadc.api.v1.model.exceptions;

public class InvalidSensorCustEx extends CustomException {

	private static final long serialVersionUID = 1L;

	public InvalidSensorCustEx() { super("The sensor specified is not valid!"); }

}
