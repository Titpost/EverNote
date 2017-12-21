package com.epam.evernote.service.Implementations;


import com.epam.evernote.dao.JdbcTemplateTagDao;
import com.epam.evernote.model.Tag;
import com.epam.evernote.service.Interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired(required = false)
    private JdbcTemplateTagDao tagDao;

    @Override
    public void saveTag(Tag tag) {
        tagDao.save(tag);
    }

    @Override
    public long getNoteTagCount(long note) {
        return tagDao.getNoteTagCount(note);
    }

    @Override
    public Long getTagCount() {
        return tagDao.getTagCount();
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.loadAll();
    }

    @Override
    public List<Tag> getAllTags(long person) {
        return tagDao.loadAll(person);
    }

    @Override
    public List<Tag> getAllNoteTags(long note) {
        return tagDao.loadAllForNote(note);
    }

    @Override
    public Tag findTagByNameAndNote(String tag, long note) {
        return tagDao.findTagByNameAndNote(tag, note);
    }

    @Override
    public boolean exists(Tag tag) {
        return tagDao.exists(tag);
    }

    @Override
    public void deleteTag(String name, long note) {
        tagDao.delete(name, note);
    }
}
