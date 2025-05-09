package com.example.demoProyect.api.v1.model.exceptions;

public class InvalidUserAccountKeyCustEx extends CustomException{

	private static final long serialVersionUID = 1L;
	
	public InvalidUserAccountKeyCustEx() {
        super("The user Account Key used is not valid!");
    }

}
