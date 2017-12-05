package com.epam.evernote;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonService {

    @Autowired
    private PersonDao dao;

    public void savePerson(Person person) {
        dao.save(person);
    }

    public Long getPersonCount() {
        return dao.getPersonCount();
    }

    public List<Person> getAllPersons() {
        return dao.loadAll();
    }

    public List<Person> getPersonsByName(String name) {
        return dao.findPersonsByName(name);
    }

    public Person getPersonById(long id) {
        return dao.load(id);
    }

    public void updateName(long id, String name) {
        dao.updateName(id, name);
    }

    public void deletePerson(long id) {
        dao.delete(id);
    }
}