package com.epam.evernote.dao;

import com.epam.evernote.model.Pad;

public interface PadDao extends Dao<Pad, String> {

    Long getPerson();

    Long getPadCount();

    Pad loadWithNotes(String id);
}
