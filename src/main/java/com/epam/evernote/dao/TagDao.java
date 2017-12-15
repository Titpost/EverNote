package com.epam.evernote.dao;

import com.epam.evernote.model.Tag;

public interface TagDao extends Dao<Tag, String> {

    String getNote();

    long getTagCount();

    Tag findTagByOwnerAndName(String id, long person);
}
