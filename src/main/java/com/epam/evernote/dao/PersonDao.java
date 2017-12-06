package com.epam.evernote.dao;

import com.epam.evernote.Dao;
import com.epam.evernote.Person;

import java.util.List;

public interface PersonDao extends Dao<Person> {

    List<Person> findPersonsByName(String name);

    Long getPersonCount();

    void updateName(long id, String newName);
}
