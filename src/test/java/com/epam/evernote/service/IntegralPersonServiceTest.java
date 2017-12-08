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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Integral test for Person Service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegralPersonServiceConfig.class)
public class IntegralPersonServiceTest {

    private final String hardName = "Some name rea11y hard to meet";

    @Autowired
    @Qualifier("personTemplateRepo")
    private PersonService personService;

    @Autowired
    private EmbeddedDatabase db;
    private static EmbeddedDatabase dataBase;

    /**
     * Database SetUp
     */
    @Before
    public void setUpDB() {
        assertNotNull(personService);
        dataBase = db;
    }

    /**
     * Create new user
     * check if table's row count incremented
     * find user by its ID, check its name
     */
    @Test
    public void createNew() {

        final int sizeBefore = personService.getAllPersons().size();

        // create one person with hard name
        Person person = Person.builder().name(hardName).active(true).build();
        long id = personService.savePerson(person);

        // check for table size
        assertEquals(personService.getAllPersons().size(), sizeBefore + 1);

        // find just created person by id
        person = personService.getPersonById(id);
        assertNotNull(person);
        assertEquals(person.getName(), hardName);
    }

    /**
     * Create new user
     * update its name
     * check if renaming by ID has worked
     * find by new name
     */
    @Test
    public void renameAndFindByName() {

        final String name1 = hardName + "_1_";
        final String name2 = hardName + "_2_";

        // create person with hard name
        Person person = Person.builder().name(name1).active(true).build();
        long id = personService.savePerson(person);

        // update name
        final int sizeBefore = personService.getAllPersons().size();
        personService.updateName(id, name2);

        // check if number of persons didn't change
        assertEquals(personService.getAllPersons().size(), sizeBefore);

        // check if person was really renamed
        assertEquals(personService.getPersonById(id).getName(), name2);

        // find person by his new name
    }

    /**
     * Create new user
     * find him by ID
     * delete this user
     * check if he can't be found by same ID
     */
    @Test
    public void deleteByIdAndCheckCount() {

        // create person with hard name
        Person person = Person.builder().name(hardName + "_3_").active(true).build();
        long id = personService.savePerson(person);
        assertNotNull(personService.getPersonById(id));
        final long sizeBefore = personService.getPersonCount();

        // delete person
        personService.deletePerson(id);

        // check if person can't be found by ID
        assertNull(personService.getPersonById(id));

        // check if table's row count decremented
        assertEquals((long)personService.getPersonCount(), sizeBefore - 1);
    }

    /**
     * Closes DataBase connection
     */
    @AfterClass
    public static void tearDown(){
        dataBase.shutdown();
    }
}