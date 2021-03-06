package com.epam.evernote.service.implementations;

import com.epam.evernote.dao.JdbcTemplateNoteDao;
import com.epam.evernote.model.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceUnitTest {

    @Mock
    private JdbcTemplateNoteDao noteDao;

    @InjectMocks
    private NoteServiceImpl noteService;

    @Before
    public void setUp() {
    }

    @Test
    public void saveNote() throws Exception {
        Note note = Note.builder()
                .padId(1)
                .name("Name")
                .build();
        noteService.saveNote(note);
        verify(noteDao, times(1)).save(note);
        verifyNoMoreInteractions(noteDao);
    }

    @Test
    public void getNoteCount() throws Exception {
        Long noteCount = 20L;
        when(noteDao.getNoteCount(1)).thenReturn(noteCount);
        Long resultNoteCount = noteService.getPadNoteCount(1);
        assertThat(resultNoteCount).isEqualTo(noteCount);

        verify(noteDao, times(1)).getNoteCount(1);
        verifyNoMoreInteractions(noteDao);
    }

    @Test
    public void getAllNotes() throws Exception {
        Note note1 = Note.builder()
                .padId(1)
                .name("Name1")
                .build();
        Note note2 = Note.builder()
                .padId(1)
                .name("Name2")
                .build();
        List<Note> note = new ArrayList<>();
        note.add(note1);
        note.add(note2);

        when(noteDao.loadAll()).thenReturn(note);

        List<Note> resultNote = noteService.getAllNotes();
        assertThat(resultNote).isEqualTo(note);

        verify(noteDao, times(1)).loadAll();
        verifyNoMoreInteractions(noteDao);
    }

    @Test
    public void getNoteById() throws Exception {
        long noteId = 1;
        String noteName = "Name";
        Note note = Note.builder()
                .padId(1)
                .name(noteName)
                .build();
        when(noteDao.load(1L)).thenReturn(note);
        Note resultNote = noteService.getNoteById(noteId);
        assertThat(resultNote).isEqualTo(note);

        verify(noteDao, times(1)).load(noteId);
        verifyNoMoreInteractions(noteDao);
    }

    @Test
    public void deleteNote() throws Exception {
        long noteId = 1;
        noteService.deleteNote(noteId);
        verify(noteDao, times(1)).delete(noteId);
        verifyNoMoreInteractions(noteDao);
    }
}
