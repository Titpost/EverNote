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
    public Long getTagCount() {
        return tagDao.getTagCount();
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.loadAll();
    }

    @Override
    public Tag getTagByOwnerAndName(Long person, String id) {
        return tagDao.load(id);
    }

    @Override
    public void deleteTag(String id) {
        tagDao.delete(id);
    }
}
