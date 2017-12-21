package com.epam.evernote.dao;

import com.epam.evernote.model.Tag;

import java.util.List;

public interface TagDao extends Dao<Tag, String> {

    Tag findTagByNameAndNote(String name, long note);

    long getTagCount();

    long getNoteTagCount(long note);

    long getNote();

    List<Tag> loadAll(long person);

    List<Tag> loadAllForNote(long note);

    void delete(String name, long note);
}
