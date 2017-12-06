package com.epam.evernote.service;

import com.epam.evernote.Model.Person;
import com.epam.evernote.config.IntegralPersonServiceConfig;
import com.epam.evernote.service.Implementations.PersonServiceImpl;
import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Integral test for Person Service.
 */
public class IntegralPersonServiceImplTest {
    private static AnnotationConfigApplicationContext daoTestContext;
    private static PersonServiceImpl personServiceImpl;

    /**
     * Database SetUp
     */
    @BeforeClass
    public static void setUpDB() {
        daoTestContext = new AnnotationConfigApplicationContext(IntegralPersonServiceConfig.class);
        personServiceImpl = daoTestContext.getBean(PersonServiceImpl.class);
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
        personServiceImpl.savePerson(person1);
        Assert.assertEquals(personServiceImpl.getAllPersons().size(), 1);

        // create another person with easy name
        Person person2 = Person.builder().name(easyName).active(true).build();
        personServiceImpl.savePerson(person2);
        Assert.assertEquals(personServiceImpl.getAllPersons().size(), 2);

        // check if person count returned correctly
        Assert.assertEquals((long) personServiceImpl.getPersonCount(), 2);

        // find by id
        final long id = person2.getId();
        person2 = personServiceImpl.getPersonById(2/*id*/);
        Assert.assertEquals(person2.getName(), easyName);

        // find by name
        List<Person> persons = personServiceImpl.getPersonsByName(hardName);
        Assert.assertEquals(persons.size(), 1);
        person1 = persons.get(0);
        Assert.assertEquals(person1.getName(), hardName);

        // update name
        final String newName = "Titov";
        personServiceImpl.updateName(2/*id*/, newName);

        // check if number of persons didn't change
        persons = personServiceImpl.getAllPersons();
        int count = persons.size();
        Assert.assertEquals(personServiceImpl.getAllPersons().size(), count);

        // check if person was really renamed
        Assert.assertEquals(personServiceImpl.getPersonById(2/*id*/).getName(), newName);

        // delete person
        personServiceImpl.deletePerson(2/*id*/);

        // check if person was really deleted
        Assert.assertNull(personServiceImpl.getPersonById(2/*id*/));
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