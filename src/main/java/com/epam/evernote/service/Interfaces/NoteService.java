package com.epam.evernote.service.Interfaces;

import com.epam.evernote.model.Note;
import java.util.List;

public interface NoteService {

    void saveNote(Note note);

    Long getNoteCount();

    List<Note> getAllNotes();

    Note getNoteById(String name);

    void deleteNote(String name);
}
