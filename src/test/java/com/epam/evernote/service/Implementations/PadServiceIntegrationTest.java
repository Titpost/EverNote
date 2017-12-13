package com.epam.evernote.service.Implementations;

import com.epam.evernote.model.Pad;
import com.epam.evernote.config.PadServiceIntegrationTestConfig;
import com.epam.evernote.service.Interfaces.PadService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
     * Find pad by its ID (name)
     */
    @Test
    public void findByName() {

        // create pad with hard name
        Pad pad = Pad.builder().name(hardName).personId(1L).build();
        padService.savePad(pad);

        // find pad by its name
        pad = padService.getPadById(hardName);
        assertNotNull(pad);
        assertEquals(hardName, pad.getName());

        // delete just created pad
        padService.deletePad(hardName);
    }

    /**
     * Try to find not existing pad by wrong ID (name)
     */
    @Test
    public void findNotExisting() {

        // find pad by its name
        Pad pad = padService.getPadById(hardName);
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
        padService.savePad(pad);
        assertNotNull(padService.getPadById(padName));
        final int count = padService.getAllPads().size();

        // delete just created pad
        padService.deletePad(padName);
        assertNull(padService.getPadById(padName));

        // check if table's row count decremented
        assertEquals(count - 1, padService.getAllPads().size());
    }

    /**
     * Delete pad with notes
     */
    @Test
    public void deleteReferred() {
        // delete pad with notes
        padService.deletePad("Pad1");
        assertNull(padService.getPadById("Pad1"));
    }

    private long getCount() {
        return padService.getPadCount();
    }
}