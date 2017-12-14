package com.epam.evernote.dao;

import com.epam.evernote.model.Pad;


public interface PadDao extends Dao<Pad, Long> {

    Pad findPadByOwnerAndName(long person, String name);

    long getPadCount();

    Pad loadWithNotes(long id);

    long getPerson();
}
