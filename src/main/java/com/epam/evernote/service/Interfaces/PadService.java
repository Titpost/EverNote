package com.epam.evernote.service.Interfaces;

import com.epam.evernote.model.Pad;
import java.util.List;

public interface PadService {

  void savePad(Pad pad);

  Long getPadCount();

  List<Pad> getAllPads();

  Pad getPadById(long id);

  void deletePad(long id);
}
