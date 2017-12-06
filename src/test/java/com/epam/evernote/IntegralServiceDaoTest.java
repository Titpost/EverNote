package com.epam.evernote;

import com.epam.evernote.config.IntegralServiceDaoConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Dao test.
 */
public class IntegralServiceDaoTest {
    private static final Logger logger = Logger.getLogger(IntegralServiceDaoTest.class.getName());

    private AnnotationConfigApplicationContext daoTestContext;

    private PersonService personService;

    @Before
    public void setUp() {
        daoTestContext = new AnnotationConfigApplicationContext(IntegralServiceDaoConfig.class);
        personService = daoTestContext.getBean(PersonService.class);
    }

    @Test
    public void testAddPerson() {

        // create 2 persons
        Person person = Person.create("Sergei", true);
        personService.savePerson(person);
        person = Person.create("Andrey", true);
        personService.savePerson(person);
        logger.info("person count " + personService.getPersonCount());

        // find by name
        List<Person> persons = personService.getPersonsByName("Andrey");
        logger.info("persons found by name " + persons);

        // find by id
        person = personService.getPersonById(2);
        logger.info("found person by id : " + person);

        // get all the person
        logger.info("All persons : " + personService.getAllPersons());
 /*   }

    @Test
    public void testUpdatePerson() {*/

        // update name
        personService.updateName(1, "Titov");
        logger.info("Address updated " + personService.getPersonById(1));

        // delete person
        personService.deletePerson(2);
        logger.info("All persons : " + personService.getAllPersons());
    }

    @After
    public void tearDown() throws SQLException {
        daoTestContext.getBean(DataSource.class).getConnection().close();
    }

}