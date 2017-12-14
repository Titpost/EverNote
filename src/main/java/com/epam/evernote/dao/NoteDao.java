package com.epam.evernote.dao;

import com.epam.evernote.model.Note;


public interface NoteDao extends Dao<Note, Long> {

    Note findNoteByOwnerAndName(long person, String name);

    long getNoteCount();

    Note loadWithTags(long id);

    long getPad();
}
