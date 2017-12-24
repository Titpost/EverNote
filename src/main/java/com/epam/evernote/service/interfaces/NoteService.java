package com.epam.evernote.service.interfaces;

import com.epam.evernote.model.Note;

import java.util.List;

public interface NoteService {

    long saveNote(Note note);

    long getPadNoteCount(long pad);

    List<Note> getAllNotes();

    List<Note> getPadNotes(long pad);

    List<Note> getAllNotes(long person);

    Note getNoteById(long id);

    Note getNoteWithTags(long id);

    boolean exists(Note note);

    void updateName(long id, String name);

    void deleteNote(long id);
}
