package com.epam.evernote.service.interfaces;

import com.epam.evernote.model.Tag;

import java.util.List;

public interface TagService {

    void saveTag(Tag tag);

    long getNoteTagCount(long pad);

    Long getTagCount();

    List<Tag> getAllTags();

    List<Tag> getAllTags(long person);

    List<Tag> getAllNoteTags(long note);

    Tag findTagByNameAndNote(String tag, long note);

    boolean exists(Tag tag);

    void deleteTag(String name, long note);
}
