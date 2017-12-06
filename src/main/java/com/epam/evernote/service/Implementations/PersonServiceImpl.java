package com.epam.evernote.service.Implementations;


import com.epam.evernote.Model.Person;
import com.epam.evernote.dao.PersonDao;
import com.epam.evernote.service.Interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao dao;

    @Override
    public void savePerson(Person person) {
        dao.save(person);
    }

    @Override
    public Long getPersonCount() {
        return dao.getPersonCount();
    }

    @Override
    public List<Person> getAllPersons() {
        return dao.loadAll();
    }

    @Override
    public List<Person> getPersonsByName(String name) {
        return dao.findPersonsByName(name);
    }

    @Override
    public Person getPersonById(long id) {
        return dao.load(id);
    }

    @Override
    public void updateName(long id, String name) {
        dao.updateName(id, name);
    }

    @Override
    public void deletePerson(long id) {
        dao.delete(id);
    }
}