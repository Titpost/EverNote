package com.epam.evernote.dao;

import com.epam.evernote.model.Note;

import java.util.List;


public interface NoteDao extends Dao<Note, Long> {

    Note findNoteById(long id);

    long getNoteCount();

    List<Note> loadAll(long pad);

    Note loadWithTags(long id);

    long getPad();
}
