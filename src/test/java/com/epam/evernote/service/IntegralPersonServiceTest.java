package com.epam.evernote.service;

import com.epam.evernote.Person;
import com.epam.evernote.config.IntegralPersonServiceConfig;
import com.epam.evernote.service.PersonService;
import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Integral test for Person Service.
 */
public class IntegralPersonServiceTest {
    private static AnnotationConfigApplicationContext daoTestContext;
    private static PersonService personService;

    /**
     * Database SetUp
     */
    @BeforeClass
    public static void setUpDB() {
        daoTestContext = new AnnotationConfigApplicationContext(IntegralPersonServiceConfig.class);
        personService = daoTestContext.getBean(PersonService.class);
    }

    /**
     * Test with series of operations within Person
     */
    @Test
    public void TestPersonService() {

        final String hardName = "Some name hardly to meet";
        final String easyName = "Sergei";

        // create one person with hard name
        personService.savePerson(Person.create(hardName, true));
        Assert.assertEquals(personService.getAllPersons().size(), 1);

        // create another person with easy name
        Person person = Person.create(easyName, true);
        personService.savePerson(person);
        Assert.assertEquals(personService.getAllPersons().size(), 2);

        // check if person count returned correctly
        Assert.assertEquals((long)personService.getPersonCount(), 2);

        // find by id
        final long id = person.getId();
        person = personService.getPersonById(2/*id*/);
        Assert.assertEquals(person.getName(), easyName);

        // find by name
        List<Person> persons = personService.getPersonsByName(hardName);
        Assert.assertEquals(persons.size(), 1);
        person = persons.get(0);
        Assert.assertEquals(person.getName(), hardName);

        // update name
        final String newName = "Titov";
        personService.updateName(2/*id*/, newName);

        // check if number of persons didn't change
        persons = personService.getAllPersons();
        int count = persons.size();
        Assert.assertEquals(personService.getAllPersons().size(), count);

        // check if person was really renamed
        Assert.assertEquals(personService.getPersonById(2/*id*/).getName(), newName);

        // delete person
        personService.deletePerson(2/*id*/);

        // check if person was really deleted
        Assert.assertNull(personService.getPersonById(2/*id*/));
    }

    /**
     * Closes DataBase connection
     * @throws SQLException if something went wrong with DB connection
     */
    @AfterClass
    public static void tearDown() throws SQLException {
        daoTestContext.getBean(DataSource.class).getConnection().close();
    }
}