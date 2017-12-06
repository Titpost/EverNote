package com.epam.evernote.service;

import com.epam.evernote.Pad;
import com.epam.evernote.Person;
import com.epam.evernote.config.IntegralPadServiceConfig;
import com.epam.evernote.service.PadService;
import com.epam.evernote.service.PersonService;
import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Integral test for Notepad Service.
 */
public class IntegralNotepadServiceTest {
    private static AnnotationConfigApplicationContext padTestContext;
    private static PersonService personService;
    private static PadService padService;

    /**
     * Database SetUp
     */
    @BeforeClass
    public static void setUpDB() {
        padTestContext = new AnnotationConfigApplicationContext(IntegralPadServiceConfig.class);
        personService = padTestContext.getBean(PersonService.class);
        padService = padTestContext.getBean(PadService.class);
    }

    /**
     * Test with series of operations within Notepad
     */
    @Test
    public void TestNotepadService() {

        final String Name1 = "Name 1";
        final String Name2 = "Name 2";

        // create one person with hard name
        personService.savePerson(Person.create(Name1, true));

        // create another person with easy name
        Person person = Person.create(Name2, true);
        personService.savePerson(person);

        // create new notepad
        Pad pad = Pad.create("Notepad 1", 1);
        padService.savePad(pad);
        Assert.assertEquals(padService.getAllPads().size(), 1);
    }

    /**
     * Closes DataBase connection
     * @throws SQLException if something went wrong with DB connection
     */
    @AfterClass
    public static void tearDown() throws SQLException {
        padTestContext.getBean(DataSource.class).getConnection().close();
    }
}