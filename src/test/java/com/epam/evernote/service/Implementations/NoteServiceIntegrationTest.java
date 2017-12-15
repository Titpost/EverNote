package com.epam.evernote.service.Implementations;

import com.epam.evernote.config.NoteServiceIntegrationTestConfig;
import com.epam.evernote.model.Note;
import com.epam.evernote.model.Tag;
import com.epam.evernote.service.Interfaces.NoteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

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
     * Try to find an existing note by ID
     */
    @Test
    public void findById() {

        final long id = 1;
        Note note = noteService.getNoteById(id);
        assertNotNull(note);
        assertEquals(id, note.getId());
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
     * Find note by its owner and Name
     */
    @Test
    public void findByOwnerAndName() {

        final String name = "Note1";
        Note note = noteService.getNoteByNameAndOwner(name, 1);
        assertNotNull(note);
        assertEquals(name, note.getName());
    }

    /**
     * Find note with its tags
     */
    @Test
    public void loadWithTags() {
        Note note = noteService.getNoteWithTags(1);
        List<Tag> tags = note.getTags();
        assertNotNull(tags);
        assertTrue(tags.size() > 0);
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
        final long id = 1;
        noteService.deleteNote(id);
        assertNull(noteService.getNoteById(id));
    }

    private long getCount() {
        return noteService.getNoteCount();
    }
}