package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
	private final int ID;
	private final String name;
	public Person(	@JsonProperty("id") int iD, 
					@JsonProperty("name") String name) {
		super();
		ID = iD;
		this.name = name;
	}
	public int getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	
}
