package com.epam.evernote.service.Interfaces;

import com.epam.evernote.model.Note;

import java.util.List;

public interface NoteService {

    long saveNote(Note note);

    long getNoteCount();

    List<Note> getAllNotes();

    Note getNoteById(long id);

    Note getNoteWithTags(long id);

    Note getNoteByNameAndOwner(String name, long person);

    void deleteNote(long id);
}
