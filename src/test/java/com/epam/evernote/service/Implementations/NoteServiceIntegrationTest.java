package com.epam.evernote.service.Implementations;

import com.epam.evernote.config.NoteServiceIntegrationTestConfig;
import com.epam.evernote.model.Note;
import com.epam.evernote.model.Pad;
import com.epam.evernote.service.Interfaces.NoteService;
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
 * Integral test for Note Service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NoteServiceIntegrationTestConfig.class)
public class NoteServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private NoteService noteService;

    /**
     * Database SetUp
     */
    @Before
    public void setUpDB() {
        assertNotNull(noteService);
        dataBase = db;
    }

    /**
     * Get notes count
     */
    @Test
    public void getAll() {
        assertEquals(getCount(), noteService.getAllNotes().size());
    }

    /**
     * Create new note
     */
    @Test
    public void createNew() {

        long prevCount = getCount();

        final String noteName = "NoteName";

        // create new note
        Note note = Note.builder().name(noteName).padId(2).text("Some Text").build();
        noteService.saveNote(note);

        // check row count
        assertEquals(prevCount + 1, getCount());
    }

    /**
     * Create 2 non unique notes
     */
    @Test
    public void createNotUniques() {

        long initialCount = getCount();

        Note note = Note.builder().name(hardName + "_nonUnique").padId(2L).build();
        noteService.saveNote(note);

        // must be +1
        assertEquals(initialCount + 1, getCount());

        // create new note with same name and pad
        noteService.saveNote(note);

        // must be + 1 (not + 2)
        assertEquals(initialCount + 1, getCount());
    }


    /**
     * Find note by its owner and Name
     */
    @Test
    public void findByOwnerAndName() {

        final String name = "Note1";
        Note note = noteService.getNoteByOwnerAndName(1L, name);
        assertNotNull(note);
        assertEquals(name, note.getName());
    }

    /**
     * Try to find not existing note by wrong ID
     */
    @Test
    public void findNotExisting() {

        Note note = noteService.getNoteById(9999);
        assertNull(note);
    }

    /**
     * Delete note
     */
    @Test
    public void deleteByIdAndCheckCount() {

        // create new note
        final String noteName = "toDelete";
        Note note = Note.builder().name(noteName).padId(1).build();
        final long id = noteService.saveNote(note);
        assertNotNull(noteService.getNoteById(id));
        final int count = noteService.getAllNotes().size();

        // delete just created note
        noteService.deleteNote(id);
        assertNull(noteService.getNoteById(id));

        // check if table's row count decremented
        assertEquals(count - 1, noteService.getAllNotes().size());
    }

    /**
     * Delete note with tags
     */
    @Test
    public void deleteReferred() {
        // delete note with tags
        noteService.deleteNote(1L);
        assertNull(noteService.getNoteById(1L));
    }

    private long getCount() {
        return noteService.getNoteCount();
    }
}