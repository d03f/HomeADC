package com.homeadc.homeadc.api.v1.model.exceptions;

public class DuplicatedEntryCustEx extends CustomException {

	private static final long serialVersionUID = 1L;

	public DuplicatedEntryCustEx() {
		super("The values chosen already exist!");
	}

}
