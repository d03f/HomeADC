package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Person;

public interface PersonDao {
	
	int insertPerson(Person person);
	
	List<Person> selectAllPersons();
	
	Optional<Person> getPersonByName(String name);
}
