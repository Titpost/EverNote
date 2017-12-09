package com.epam.evernote.dao;

import com.epam.evernote.model.Note;

public interface NoteDao extends Dao<Note, String> {

    Long getPad();

    Long getNoteCount();
}
