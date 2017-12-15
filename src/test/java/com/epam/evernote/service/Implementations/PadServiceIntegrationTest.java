package com.epam.evernote.service.Implementations;

import com.epam.evernote.model.Pad;
import com.epam.evernote.config.PadServiceIntegrationTestConfig;
import com.epam.evernote.service.Interfaces.PadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * Integral test for Pad Service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PadServiceIntegrationTestConfig.class)
public class PadServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private PadService padService;

    /**
     * Database SetUp
     */
    @Before
    public void setUpDB() {
        assertNotNull(padService);
        dataBase = db;
    }

    /**
     * Get pads count
     */
    @Test
    public void getAll() {
        assertEquals(getCount(), padService.getAllPads().size());
    }

    /**
     * Try to find an existing note by ID
     */
    @Test
    public void findById() {

        final long id = 1;
        Pad pad  = padService.getPadById(id);
        assertNotNull(pad);
        assertEquals(id, pad.getId());
    }

    /**
     * Create new pad
     */
    @Test
    public void createNew() {

        long prevCount = getCount();

        final String padName = "PadName";

        // create new pad
        Pad pad = Pad.builder().name(padName).personId(1L).build();
        padService.savePad(pad);

        // check row count
        assertEquals(prevCount + 1, getCount());
    }


    /**
     * Create 2 non unique pads
     */
    @Test
    public void createNotUniques() {

        long initialCount = getCount();

        // create new pad
        Pad pad = Pad.builder().name(hardName + "_nonUnique").personId(1L).build();
        padService.savePad(pad);

        // must be +1
        assertEquals(initialCount + 1, getCount());

        // create new pad with same name and person
        padService.savePad(pad);

        // must be + 1 (not + 2)
        assertEquals(initialCount + 1, getCount());
    }

    /**
     * Find pad by its owner and Name
     */
    @Test
    public void findByOwnerAndName() {

        // find pad by its name
        final String name = "Pad2";
        Pad pad = padService.getPadByOwnerAndName(1L, name);
        assertNotNull(pad);
        assertEquals(name, pad.getName());
    }

    /**
     * Try to find not existing pad by wrong ID
     */
    @Test
    public void findNotExisting() {

        // find pad by its id
        Pad pad = padService.getPadById(7777L);
        assertNull(pad);
    }

    /**
     * Delete pad
     */
    @Test
    public void deleteByIdAndCheckCount() {

        // create new pad
        final String padName = "toDelete";
        Pad pad = Pad.builder().name(padName).personId(1L).build();
        Long newId = padService.savePad(pad);
        assertNotNull(padService.getPadById(newId));
        final int count = padService.getAllPads().size();

        // delete just created pad
        padService.deletePad(newId);
        assertNull(padService.getPadById(newId));

        // check if table's row count decremented
        assertEquals(count - 1, padService.getAllPads().size());
    }

    /**
     * Delete pad with notes
     */
    @Test
    public void deleteReferred() {
        // delete pad with notes
        padService.deletePad(1L);
        assertNull(padService.getPadById(1L));
    }

    private long getCount() {
        return padService.getPadCount();
    }
}