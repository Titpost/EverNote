package com.epam.evernote.dao;

import com.epam.evernote.model.Pad;


public interface PadDao extends Dao<Pad, Long> {

    Pad findPadByNameAndOwner(String name, long person);

    long getPadCount();

    Pad loadWithNotes(long id);

    long getPerson();
}
