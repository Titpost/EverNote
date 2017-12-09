package com.epam.evernote.service.Implementations;


import com.epam.evernote.dao.JdbcTemplatePadDao;
import com.epam.evernote.model.Pad;
import com.epam.evernote.service.Interfaces.PadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("padTemplateRepo") /// TODO Why is it a Repo, not a Service?
public class PadServiceImpl implements PadService {

    @Autowired(required = false)
    private JdbcTemplatePadDao padDao;

    @Override
    public void savePad(Pad pad) {
        padDao.save(pad);
    }

    @Override
    public Long getPadCount() {
        return padDao.getPadCount();
    }

    @Override
    public List<Pad> getAllPads() {
        return padDao.loadAll();
    }

    @Override
    public Pad getPadById(String id) {
        return padDao.load(id);
    }

    @Override
    public void deletePad(String id) {
        padDao.delete(id);
    }
}
