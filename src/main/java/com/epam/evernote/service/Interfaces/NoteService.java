package com.epam.evernote.service.Interfaces;

import com.epam.evernote.model.Note;
import com.epam.evernote.model.Pad;

import java.util.List;

public interface NoteService {

    long saveNote(Note note);

    long getNoteCount();

    List<Note> getAllNotes();

    List<Note> getAllNotes(long pad);

    Note getNoteById(long id);

    Note getNoteWithTags(long id);

    boolean exists(Note note);

    void updateName(long id, String name);

    void deleteNote(long id);
}
