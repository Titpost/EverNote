package com.epam.evernote.dao;

import com.epam.evernote.model.Pad;


public interface PadDao extends Dao<Pad, Long> {

    Pad findPadByOwnerAndName(Long person, String name);

    Long getPadCount();

    Pad loadWithNotes(Long id);

    Long getPerson();
}
