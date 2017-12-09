package com.epam.evernote.service;

import com.epam.evernote.model.Pad;
import com.epam.evernote.model.Person;
import com.epam.evernote.config.PadServiceTestIntegralTestConfig;
import com.epam.evernote.service.Interfaces.PadService;
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
 * Integral test for Notepad Service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PadServiceTestIntegralTestConfig.class)
public class NotepadServiceIntegralTest {

    @Autowired
    @Qualifier("personTemplateRepo")
    private PersonService personService;

    @Autowired
    @Qualifier("padTemplateRepo")
    private PadService padService;

    @Autowired
    private EmbeddedDatabase db;
    private static EmbeddedDatabase dataBase;

    /**
     * Database SetUp
     */
    @Before
    public void setUpDB() {
        assertNotNull(personService);
        assertNotNull(padService);
        dataBase = db;
    }

    /**
     * Test with series of operations within Notepad
     */
    @Test
    public void TestNotepadService() {

        final String Name1 = "Name 1";
        final String Name2 = "Name 2";

        // create one person
        Person person1 = Person.builder().name(Name1).active(true).build();
        personService.savePerson(person1);

        // create another person
        Person person2 = Person.builder().name(Name2).active(true).build();
        personService.savePerson(person2);

        // create new notepad
        Pad pad = Pad.builder().id(1L).name("Sample Notepad").build();
        padService.savePad(pad);
        Assert.assertEquals(padService.getAllPads().size(), 1);
    }

    /**
     * Closes DataBase connection
     */
    @AfterClass
    public static void tearDown() {
        dataBase.shutdown();
    }
}