package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;


@Repository("fakeDao")
public class FakePersonDBAccess implements PersonDao{
	private static java.util.List<Person> db = new java.util.ArrayList<>();
	
	@Override
	public int insertPerson(Person person) {
		db.add(person);
		return 1;
	}

	@Override
	public List<Person> selectAllPersons() {
		return db;
	}

	@Override
	public Optional<Person> getPersonByName(String name) {
		return db.stream().filter(person -> person.getName().equals(name)).findFirst();
	}
	
	
	

}
