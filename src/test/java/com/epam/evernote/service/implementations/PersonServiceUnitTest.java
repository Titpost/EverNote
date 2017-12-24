package com.epam.evernote.service.implementations;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PersonServiceUnitTest {

    @Mock
    private JdbcTemplatePersonDao dao;

    @InjectMocks
    private PersonServiceImpl personService;

    @Before
    public void setUp() {
    }


    @Test
    public void savePerson() throws Exception {
        Person person = Person.builder().id(1)
                .name("Name")
                .password("hashpassword")
                .active(true)
                .build();
        personService.savePerson(person);
        verify(dao, times(1)).save(person);
        verifyNoMoreInteractions(dao);
    }

    @Test
    public void getPersonCount() throws Exception {
        Long personCount = 20L;
        when(dao.getPersonCount()).thenReturn(personCount);
        Long resultPersonCount = personService.getPersonCount();
        assertThat(resultPersonCount).isEqualTo(personCount);

        verify(dao, times(1)).getPersonCount();
        verifyNoMoreInteractions(dao);
    }

    @Test
    public void getAllPersons() throws Exception {
        Person person1 = Person.builder().id(1)
                .name("Name1")
                .password("hashpassword")
                .active(true)
                .build();
        Person person2 = Person.builder().id(1)
                .name("Name2")
                .password("hashpassword")
                .active(true)
                .build();
        List<Person> person = new ArrayList<>();
        person.add(person1);
        person.add(person2);

        when(dao.loadAll()).thenReturn(person);

        List<Person> resultPerson = personService.getAllPersons();
        assertThat(resultPerson).isEqualTo(person);

        verify(dao, times(1)).loadAll();
        verifyNoMoreInteractions(dao);

    }

    @Test
    public void getPersonsByName_PersonIsFind() throws Exception {
        Person person1 = Person.builder().id(1)
                .name("Name1")
                .password("hashpassword")
                .active(true)
                .build();
        Person person2 = Person.builder().id(1)
                .name("Name1")
                .password("hashpassword")
                .active(true)
                .build();
        List<Person> person = new ArrayList<>();
        person.add(person1);
        person.add(person2);
        String name = "Name1";
        when(dao.findPersonsByName(name)).thenReturn(person);

        List<Person> resultPerson = personService.getPersonsByName(name);
        assertThat(resultPerson).isEqualTo(person);

        verify(dao, times(1)).findPersonsByName(name);
        verifyNoMoreInteractions(dao);

    }

    @Test
    public void getPersonsByName_PersonNotFind() throws Exception {

        List<Person> person = Collections.emptyList();
        String name = "Name2";
        when(dao.findPersonsByName(name)).thenReturn(Collections.emptyList());

        List<Person> resultPerson = personService.getPersonsByName(name);
        assertThat(resultPerson).isEqualTo(person);

        verify(dao, times(1)).findPersonsByName(name);
        verifyNoMoreInteractions(dao);

    }


    @Test
    public void getPersonById() throws Exception {
        Person person = Person.builder().id(1)
                .name("Name")
                .password("hashpassword")
                .active(true)
                .build();
        long personId = 1;
        when(dao.load(personId)).thenReturn(person);
        Person resultPerson = personService.getPersonById(personId);
        assertThat(resultPerson).isEqualTo(person);

        verify(dao, times(1)).load(personId);
        verifyNoMoreInteractions(dao);

    }

    @Test
    public void updateName() throws Exception {
        long id = 1;
        String name = "Name";
        personService.updateName(id, name);
        verify(dao, times(1)).updateName(id, name);
        verifyNoMoreInteractions(dao);

    }

    @Test
    public void deletePerson() throws Exception {
        long id = 1;
        personService.deletePerson(id);
        verify(dao, times(1)).delete(id);
        verifyNoMoreInteractions(dao);
    }
}
