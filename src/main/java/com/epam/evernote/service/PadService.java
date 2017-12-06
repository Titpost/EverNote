package com.epam.evernote.service;


import com.epam.evernote.Pad;
import com.epam.evernote.dao.PadDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PadService {

    @Autowired
    private PadDao dao;

    public void savePad(Pad pad) {
        dao.save(pad);
    }

    public Long getPadCount() {
        return dao.getPadCount();
    }

    public List<Pad> getAllPads() {
        return dao.loadAll();
    }

    public Pad getPadById(long id) {
        return dao.load(id);
    }

    public void deletePad(long id) {
        dao.delete(id);
    }
}