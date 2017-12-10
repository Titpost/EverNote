package com.epam.evernote.service.Implementations;

import com.epam.evernote.config.NoteServiceIntegrationTestConfig;
import com.epam.evernote.model.Note;
import com.epam.evernote.service.Interfaces.NoteService;
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
 * Integral test for Note Service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NoteServiceIntegrationTestConfig.class)
public class NoteServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    @Qualifier("noteTemplateRepo")
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
        Note note = Note.builder().name(noteName).padId("Pad1").text("Some Text").build();
        noteService.saveNote(note);

        // check row count
        assertEquals(prevCount + 1, getCount());
    }

    /**
     * Find note by its ID (name)
     */
    @Test
    public void findByName() {

        // create note with hard name
        Note note = Note.builder().name(hardName).padId("Pad1").build();
        noteService.saveNote(note);

        // find note by its name
        note = noteService.getNoteById(hardName);
        assertNotNull(note);
        assertEquals(hardName, note.getName());

        // delete just created note
        noteService.deleteNote(hardName);
    }

    /**
     * Try to find not existing note by wrong ID (name)
     */
    @Test
    public void findNotExisting() {

        // find note by its name
        Note note = noteService.getNoteById(hardName);
        assertNull(note);
    }

    /**
     * Delete note
     */
    @Test
    public void deleteByIdAndCheckCount() {

        // create new notepad
        final String padName = "toDelete";
        Note note = Note.builder().name(padName).padId("Pad1").build();
        noteService.saveNote(note);
        assertNotNull(noteService.getNoteById(padName));
        final int count = noteService.getAllNotes().size();

        // delete just created note
        noteService.deleteNote(padName);
        assertNull(noteService.getNoteById(padName));

        // check if table's row count decremented
        assertEquals(count - 1, noteService.getAllNotes().size());
    }

    /**
     * Delete note with tags
     */
    @Test
    public void deleteReferred() {
        // delete note with tags
        noteService.deleteNote("Note1");
        assertNull(noteService.getNoteById("Note1"));
    }

    private long getCount() {
        return noteService.getNoteCount();
    }
}