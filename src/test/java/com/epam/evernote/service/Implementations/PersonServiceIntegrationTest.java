package com.epam.evernote.service.Implementations;

import com.epam.evernote.model.Person;
import com.epam.evernote.config.PersonServiceIntegrationTestConfig;
import com.epam.evernote.service.Interfaces.PersonService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Integral test for Person Service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersonServiceIntegrationTestConfig.class)
public class PersonServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private PersonService personService;

    /**
     * Database SetUp
     */
    @Before
    public void setUpDB() {
        assertNotNull(personService);
        dataBase = db;
    }

    /**
     * Get persons count
     */
    @Test
    public void getAll() {
        assertEquals(getCount(), personService.getAllPersons().size());
    }

    /**
     * Find notepad by its ID (name)
     */
    @Test
    public void findByName() {

        // find person by his name
        Person person = personService.getPersonById(1);
        assertNotNull(person);
        assertEquals("Name1", person.getName());
    }

    /**
     * Try to find not existing notepad by wrong ID (name)
     */
    @Test
    public void findNotExisting() {

        // find person by his name
        Person person = personService.getPersonById(9999);
        assertNull(person);
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
        assertEquals(sizeBefore + 1, personService.getAllPersons().size());

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
        assertEquals(sizeBefore, personService.getAllPersons().size());

        // check if person was really renamed
        assertEquals(name2, personService.getPersonById(id).getName());

        // find person by his new name
        List<Person> personList = personService.getPersonsByName(name2);
        assertEquals(1, personList.size());
        assertEquals(name2, personList.get(0).getName());
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
        assertEquals(sizeBefore - 1, (long)personService.getPersonCount());
    }

    private long getCount() {
        return personService.getPersonCount();
    }
}