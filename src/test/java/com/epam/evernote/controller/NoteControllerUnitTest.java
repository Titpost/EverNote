package com.epam.evernote.controller;

import com.epam.evernote.filter.CORSFilter;
import com.epam.evernote.model.Note;
import com.epam.evernote.service.Interfaces.NoteService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class NoteControllerUnitTest extends ControllerUnitTest {

    {
        URL_BASE += "/1/pad/1/note";
        URL_CURRENT = URL_BASE + "/{id}";
    }

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(noteController)
                .addFilters(new CORSFilter())
                .build();
    }

    // =========================================== Get All Notes ==========================================

    @Test
    public void test_get_all_success() throws Exception {
        final long padId = 1;
        Note note1 = Note.builder().id(1)
                .name("Daenerys Targaryen")
                .padId(padId)
                .build();
        Note note2 = Note.builder().id(2)
                .name("John Snow")
                .padId(padId)
                .build();
        List<Note> notes = Arrays.asList(
                note1,
                note2);

        when(noteService.getAllNotes(padId)).thenReturn(notes);

        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Daenerys Targaryen")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("John Snow")))
        ;

        verify(noteService, times(1)).getAllNotes(padId);
        verifyNoMoreInteractions(noteService);
    }

    // =========================================== Get Note By ID =========================================

    @Test
    public void test_get_by_id_success() throws Exception {
        final long id = 1;
        Note note = Note.builder().id(id)
                .name("Daenerys Targaryen")
                .padId(1)
                .build();

        when(noteService.getNoteById(id)).thenReturn(note);

        mockMvc.perform(get(URL_CURRENT, id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is((int)id)))
                .andExpect(jsonPath("$.name", is("Daenerys Targaryen")))
        ;

        verify(noteService, times(1)).getNoteById(id);
        verifyNoMoreInteractions(noteService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {

        when(noteService.getNoteById(9999)).thenReturn(null);

        mockMvc.perform(get(URL_CURRENT, 1))
                .andExpect(status().isNotFound());

        verify(noteService, times(1)).getNoteById(1L);
        verifyNoMoreInteractions(noteService);
    }

    // =========================================== Create New Note ========================================

    @Test
    public void test_create_notes_success() throws Exception {
        final long noteId = 3;
        Note note = Note.builder().id(noteId)
                .name("Arya Stark")
                .padId(1)
                .build();

        when(noteService.exists(note)).thenReturn(false);

        mockMvc.perform(
                post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(note)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location",
                        containsString("http://localhost/person/1/pad/1/note")));

        verify(noteService, times(1)).exists(note);
        verify(noteService, times(1)).saveNote(note);
        verifyNoMoreInteractions(noteService);
    }

    @Test
    public void test_create_note_fail_409_conflict() throws Exception {
        Note note = Note.builder().id(3)
                .name("notename exists")
                .padId(1)
                .build();

        when(noteService.exists(note)).thenReturn(true);

        mockMvc.perform(
                post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(note)))
                .andExpect(status().isConflict());

        verify(noteService, times(1)).exists(note);
        verifyNoMoreInteractions(noteService);
    }

    // =========================================== Update Existing Note ===================================

    @Test
    public void test_update_note_success() throws Exception {
        Note note = Note.builder().id(3)
                .name("Arya Stark")
                .padId(1)
                .build();
        when(noteService.getNoteById(note.getId())).thenReturn(note);
        doNothing().when(noteService).updateName(3, note.getName());

        mockMvc.perform(
                put(URL_CURRENT, note.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(note)))
                .andExpect(status().isOk());

        verify(noteService, times(1)).getNoteById(note.getId());
        verify(noteService, times(1)).updateName(1, note.getName());
        verifyNoMoreInteractions(noteService);
    }

    @Test
    public void test_update_note_fail_404_not_found() throws Exception {
        Note note = Note.builder().id(1)
                .name("note not found")
                .padId(1)
                .build();

        when(noteService.getNoteById(note.getId())).thenReturn(null);

        mockMvc.perform(
                put(URL_CURRENT, note.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(note)))
                .andExpect(status().isNotFound());

        verify(noteService, times(1)).getNoteById(note.getId());
        verifyNoMoreInteractions(noteService);
    }

    // =========================================== Delete Note ============================================

    @Test
    public void test_delete_note_success() throws Exception {
        Note note = Note.builder().id(3)
                .name("Arya Stark")
                .padId(1)
                .build();

        when(noteService.getNoteById(note.getId())).thenReturn(note);
        doNothing().when(noteService).deleteNote(note.getId());

        mockMvc.perform(
                delete(URL_CURRENT, note.getId()))
                .andExpect(status().isOk());

        verify(noteService, times(1)).getNoteById(note.getId());
        verify(noteService, times(1)).deleteNote(note.getId());
        verifyNoMoreInteractions(noteService);
    }

    @Test
    public void test_delete_note_fail_404_not_found() throws Exception {
        Note note = Note.builder().id(1)
                .name("note not found")
                .padId(1)
                .build();

        when(noteService.getNoteById(note.getId())).thenReturn(null);

        mockMvc.perform(
                delete(URL_CURRENT, note.getId()))
                .andExpect(status().isNotFound());

        verify(noteService, times(1)).getNoteById(note.getId());
        verifyNoMoreInteractions(noteService);
    }

    @Test
    public void test_cors_headers() throws Exception {
        super.test_cors_headers();
    }
}
