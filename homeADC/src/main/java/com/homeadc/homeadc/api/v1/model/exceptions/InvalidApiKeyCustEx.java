package com.homeadc.homeadc.api.v1.model.exceptions;

public class InvalidApiKeyCustEx extends CustomException{

	private static final long serialVersionUID = 1L;

	public InvalidApiKeyCustEx() {
		super("The api key is not valid!");
	}

}
