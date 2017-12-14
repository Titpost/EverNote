package com.epam.evernote.service.Interfaces;

import com.epam.evernote.model.Person;
import java.util.List;

public interface PersonService {

  long savePerson(Person person);

  Long getPersonCount();

  List<Person> getAllPersons();

  List<Person> getPersonsByName(String name);

  Person getPersonById(long id);

  void updateName(long id, String name);

  void deletePerson(long id);
}
