package com.epam.evernote.service.Implementations;

import com.epam.evernote.model.Pad;
import com.epam.evernote.model.Person;
import com.epam.evernote.config.PadServiceIntegralTestConfig;
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
@ContextConfiguration(classes = PadServiceIntegralTestConfig.class)
public class PadServiceIntegralTest extends ServiceIntegralTest {

    @Autowired
    @Qualifier("personTemplateRepo")
    private PersonService personService;

    @Autowired
    @Qualifier("padTemplateRepo")
    private PadService padService;

    @Autowired
    private EmbeddedDatabase db;

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
     * Get notepads count
     */
    @Test
    public void getAll() {
        assertEquals((long)padService.getPadCount(), padService.getAllPads().size());
    }

    /**
     * Create new notepad
     */
    @Test
    public void createNew() {

        final String personName = "Name1";
        final String padName = "Name2";

        // create new person
        Person person = Person.builder().name(personName).active(true).build();
        long personId = personService.savePerson(person);

        // create new notepad
        Pad pad = Pad.builder().name(padName).personId(personId).build();
        padService.savePad(pad);

        // check row count
        assertEquals(3, padService.getAllPads().size());
        assertEquals(3, (long)padService.getPadCount());
    }

    /**
     * Find notepad by its ID (name)
     */
    @Test
    public void findByName() {

        // create notepad with hard name
        Pad pad = Pad.builder().name(hardName).personId(1L).build();
        padService.savePad(pad);

        // find notepad by its name
        pad = padService.getPadById(hardName);
        assertNotNull(pad);
        assertEquals(hardName, pad.getName());

        // delete just created notepad
        padService.deletePad(hardName);
    }

    /**
     * Try to find not existing notepad by wrong ID (name)
     */
    @Test
    public void findNotExisting() {

        // find notepad by its name
        Pad pad = padService.getPadById(hardName);
        assertNull(pad);
    }

    /**
     * Delete notepad
     */
    @Test
    public void deleteByIdAndCheckCount() {

        // create new notepad
        final String padName = "toDelete";
        Pad pad = Pad.builder().name(padName).personId(1L).build();
        padService.savePad(pad);
        assertNotNull(padService.getPadById(padName));
        final int count = padService.getAllPads().size();

        // delete just created notepad
        padService.deletePad(padName);
        assertNull(padService.getPadById(padName));

        // check if table's row count decremented
        assertEquals(count - 1, padService.getAllPads().size());
    }
}