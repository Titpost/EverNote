package com.epam.evernote.dao;

import com.epam.evernote.model.Person;

import java.util.List;

public interface PersonDao extends Dao<Person, Long> {

    List<Person> findPersonsByName(String name);

    long getPersonCount();

    void updateName(long id, String newName);
}
