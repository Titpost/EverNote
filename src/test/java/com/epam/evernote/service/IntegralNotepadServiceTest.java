package com.epam.evernote.service;

import com.epam.evernote.Model.Pad;
import com.epam.evernote.Model.Person;
import com.epam.evernote.config.IntegralPadServiceConfig;
import com.epam.evernote.service.Implementations.PadServiceImpl;
import com.epam.evernote.service.Implementations.PersonServiceImpl;
import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Integral test for Notepad Service.
 */
public class IntegralNotepadServiceTest {
    private static AnnotationConfigApplicationContext padTestContext;
    private static PersonServiceImpl personServiceImpl;
    private static PadServiceImpl padServiceImpl;

    /**
     * Database SetUp
     */
    @BeforeClass
    public static void setUpDB() {
        padTestContext = new AnnotationConfigApplicationContext(IntegralPadServiceConfig.class);
        personServiceImpl = padTestContext.getBean(PersonServiceImpl.class);
        padServiceImpl = padTestContext.getBean(PadServiceImpl.class);
    }

    /**
     * Test with series of operations within Notepad
     */
    @Test
    public void TestNotepadService() {

        final String Name1 = "Name 1";
        final String Name2 = "Name 2";

        // create one person with hard name
        personServiceImpl.savePerson(Person.create(Name1, true));

        // create another person with easy name
        Person person = Person.create(Name2, true);
        personServiceImpl.savePerson(person);

        // create new notepad
        Pad pad = Pad.create("Notepad 1", 1);
        padServiceImpl.savePad(pad);
        Assert.assertEquals(padServiceImpl.getAllPads().size(), 1);
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