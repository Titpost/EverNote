package com.epam.evernote.service.Implementations;


import com.epam.evernote.dao.JdbcTemplatePadDao;
import com.epam.evernote.model.Pad;
import com.epam.evernote.service.Interfaces.PadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PadServiceImpl implements PadService {

    @Autowired(required = false)
    private JdbcTemplatePadDao padDao;

    @Override
    public long savePad(Pad pad) {
        return padDao.save(pad);
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
    public List<Pad> getAllPads(long person) {
        return padDao.loadAll(person);
    }

    @Override
    public Pad getPadById(Long id) {
        return padDao.load(id);
    }

    @Override
    public Pad getPadWithNotes(long id) {
        return padDao.loadWithNotes(id);
    }

    @Override
    public Pad getPadByIdAndOwner(long id, long person) {
        return padDao.findPadByIdAndOwner(id, person);
    }

    @Override
    public boolean exists(Pad pad) {
        return padDao.exists(pad);
    }

    @Override
    public void updateName(long id, String name) {
        padDao.updateName(id, name);
    }

    @Override
    public void deletePad(Long id) {
        padDao.delete(id);
    }
}
