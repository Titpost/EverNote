package com.epam.evernote.dao;

import com.epam.evernote.Model.Pad;

public interface PadDao extends Dao<Pad> {

    Long getPerson();

    Long getPadCount();
}
