package com.epam.evernote.dao;

import com.epam.evernote.model.Pad;

public interface PadDao extends Dao<Pad> {

    Long getPerson();

    Long getPadCount();
}
