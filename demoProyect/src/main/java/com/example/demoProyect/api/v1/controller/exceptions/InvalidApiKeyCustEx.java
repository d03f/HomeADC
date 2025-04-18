package com.example.demoProyect.api.v1.controller.exceptions;

public class InvalidApiKeyCustEx extends CustomException{

	private static final long serialVersionUID = 1L;

	public InvalidApiKeyCustEx() {
		super("The api key is not valid!");
	}

}
