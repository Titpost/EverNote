package com.epam.evernote.service.Implementations;


import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.model.Person;
import com.epam.evernote.service.Interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("personTemplateRepo")   /// TODO Why is it a Repo, not a Service?
public class PersonServiceImpl implements PersonService {

    @Autowired(required = false)
    private JdbcTemplatePersonDao dao;

    @Override
    public long savePerson(Person person) {
        return dao.save(person);
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
