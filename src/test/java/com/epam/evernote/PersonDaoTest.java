package com.epam.evernote;

import config.DaoTest;
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
public class PersonDaoTest {
    private static final Logger logger = Logger.getLogger(PersonDaoTest.class.getName());

    private AnnotationConfigApplicationContext daoTestContext;

    PersonService personService;

    @Before
    public void setUp() {
        daoTestContext = new AnnotationConfigApplicationContext(DaoTest.class);
        personService = daoTestContext.getBean(PersonService.class);
    }

    @Test
    public void testAddPerson() {

        Person person = Person.create("Sergei", true);
        personService.savePerson(person);

        person = Person.create("Andrey", true);
        personService.savePerson(person);
        logger.info("person count " + personService.getPersonCount());

        List<Person> persons = personService.getPersonsByName("Andrey");
        logger.info("persons found by name " + persons);

        person = personService.getPersonById(2);
        logger.info("found person by id : " + person);
        logger.info("All persons : " + personService.getAllPersons());

        personService.updateName(1, "Titov");
        logger.info("Address updated " + personService.getPersonById(1));

        personService.deletePerson(2);
        logger.info("All persons : " + personService.getAllPersons());
    }

    @After
    public void tearDown() throws SQLException {
        daoTestContext.getBean(DataSource.class).getConnection().close();
    }

}