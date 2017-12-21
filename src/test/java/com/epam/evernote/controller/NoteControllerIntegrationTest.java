package com.epam.evernote.controller;


import com.epam.evernote.config.ApiControllerIntegrationTest;
import com.epam.evernote.model.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;

import static junit.framework.TestCase.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApiControllerIntegrationTest.class})
public class NoteControllerIntegrationTest extends ControllerIntegrationTest {

    {
        BASE_URI += "/1/pad/1/note";
    }

    // =========================================== Get All the Notes ==========================================

    @Test
    public void test_get_all_success(){
        ResponseEntity<Note[]> response = template.getForEntity(BASE_URI, Note[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    // =========================================== Get Note By ID =========================================

    @Test
    public void test_get_by_id_success(){
        ResponseEntity<Note> response = template.getForEntity(BASE_URI + "/1", Note.class);
        Note note = response.getBody();
        assertThat(note.getId(), is(1L));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    @Test
    public void test_get_by_id_failure_not_found(){
        try {
            ResponseEntity<Note> response = template.getForEntity(BASE_URI + "/" + UNKNOWN_ID, Note.class);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    // =========================================== Create New Note ========================================

    @Test
    public void test_create_new_note_success(){
        Note newNote = Note.builder().id(888)
                .name("new note" + Math.random())
                .padId(1)
                .build();
        URI location = template.postForLocation(BASE_URI, newNote, Note.class);
        assertThat(location, notNullValue());
    }

    @Test
    public void test_create_new_note_fail_exists(){
        Note existingNote = Note.builder().id(1)
                .name("new note" + Math.random())
                .padId(1)
                .build();
        try {
            template.postForLocation(BASE_URI, existingNote, Note.class);
            fail("should return 409 conflict");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.CONFLICT));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    // =========================================== Update Existing Note ===================================

    @Test
    public void test_update_note_success(){
        Note existingNote = Note.builder().id(2)
                .name("NoteName4")
                .padId(1)
                .build();
        template.put(BASE_URI + "/" + existingNote.getId(), existingNote);
    }

    @Test
    public void test_update_note_fail(){
        Note existingNote = Note.builder().id(UNKNOWN_ID)
                .name("update")
                .padId(1)
                .build();
        try {
            template.put(BASE_URI + "/" + existingNote.getId(), existingNote);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    // =========================================== Delete Note ============================================

    @Test
    public void test_delete_note_success(){
        template.delete(BASE_URI + "/" + getLastNote().getId());
    }

    @Test
    public void test_delete_note_fail(){
        try {
            template.delete(BASE_URI + "/" + UNKNOWN_ID);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    private Note getLastNote(){
        ResponseEntity<Note[]> response = template.getForEntity(BASE_URI, Note[].class);
        Note[] notes = response.getBody();
        return notes[notes.length - 1];
    }
}
