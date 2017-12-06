package com.epam.evernote.dao;

import com.epam.evernote.Dao;
import com.epam.evernote.Pad;

public interface PadDao extends Dao<Pad> {

    Long getPerson();

    Long getPadCount();
}
