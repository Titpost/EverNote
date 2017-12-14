package com.epam.evernote.service.Interfaces;

import com.epam.evernote.model.Tag;

import java.util.List;

public interface TagService {

    void saveTag(Tag tag);

    Long getTagCount();

    List<Tag> getAllTags();

    Tag getTagById(String name);

    void deleteTag(String name);
}
