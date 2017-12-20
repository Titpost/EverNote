package com.epam.evernote.dao;

import com.epam.evernote.model.Pad;

import java.util.List;


public interface PadDao extends Dao<Pad, Long> {

    Pad findPadByIdAndOwner(long id, long person);

    long getPadCount();

    List<Pad> loadAll(long person);

    Pad loadWithNotes(long id);

    long getPerson();

    void updateName(long id, String newName);
}
