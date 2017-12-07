package com.epam.evernote.service;

import com.epam.evernote.model.Person;
import com.epam.evernote.config.IntegralPersonServiceConfig;
import com.epam.evernote.service.Interfaces.PersonService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Integral test for Person Service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegralPersonServiceConfig.class)
public class IntegralPersonServiceImplTest {

    @Autowired
    @Qualifier("personTemplateRepo")
    private PersonService personService;

    @Autowired
    private EmbeddedDatabase db;

    /**
     * Database SetUp
     */
    @Before
    public void setUpDB() {
        assertNotNull(personService);
    }

    /**
     * Test with series of operations within Person
     */
    @Test
    public void TestPersonService() {

        final String hardName = "Some name hardly to meet";
        final String easyName = "Sergei";

        // create one person with hard name
        Person person1 = Person.builder().name(hardName).active(true).build();
        personService.savePerson(person1);
        Assert.assertEquals(personService.getAllPersons().size(), 1);

        // create another person with easy name
        Person person2 = Person.builder().name(easyName).active(true).build();
        long id = personService.savePerson(person2);
        Assert.assertEquals(personService.getAllPersons().size(), 2);

        // check if person count returned correctly
        Assert.assertEquals((long) personService.getPersonCount(), 2);

        // find by id
        person2 = personService.getPersonById(id);
        Assert.assertEquals(person2.getName(), easyName);

        // find by name
        List<Person> persons = personService.getPersonsByName(hardName);
        Assert.assertEquals(persons.size(), 1);
        person1 = persons.get(0);
        Assert.assertEquals(person1.getName(), hardName);

        // update name
        final String newName = "Titov";
        personService.updateName(id, newName);

        // check if number of persons didn't change
        persons = personService.getAllPersons();
        int count = persons.size();
        Assert.assertEquals(personService.getAllPersons().size(), count);

        // check if person was really renamed
        Assert.assertEquals(personService.getPersonById(id).getName(), newName);

        // delete person
        personService.deletePerson(id);

        // check if person was really deleted
        Assert.assertNull(personService.getPersonById(id));
    }

    /**
     * Closes DataBase connection
     */
    @After
    public void tearDown(){
        db.shutdown();
    }
}