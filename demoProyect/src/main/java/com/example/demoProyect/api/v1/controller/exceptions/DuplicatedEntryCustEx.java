package com.example.demoProyect.api.v1.controller.exceptions;

public class DuplicatedEntryCustEx extends CustomException {

	private static final long serialVersionUID = 1L;

	public DuplicatedEntryCustEx() {
		super("The values choosen already exist!");
	}

}
