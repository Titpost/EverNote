package com.epam.evernote.dao;

import com.epam.evernote.model.Note;


public interface NoteDao extends Dao<Note, Long> {

    Note findNoteByNameAndOwner(String name, long person);

    long getNoteCount();

    Note loadWithTags(long id);

    long getPad();
}
