package com.epam.evernote.service.Implementations;


import com.epam.evernote.model.Pad;
import com.epam.evernote.dao.PadDao;
import com.epam.evernote.service.Interfaces.PadService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PadServiceImpl implements PadService {

  @Autowired
  private PadDao dao;

  @Override
  public void savePad(Pad pad) {
    dao.save(pad);
  }

  @Override
  public Long getPadCount() {
    return dao.getPadCount();
  }

  @Override
  public List<Pad> getAllPads(){
    return dao.loadAll();
  }

  @Override
  public Pad getPadById(long id) {

    return dao.load(id);
  }

  @Override
  public void deletePad(long id) {
    dao.delete(id);
  }
}