package com.epam.evernote.service.Interfaces;

import com.epam.evernote.model.Pad;
import java.util.List;

public interface PadService {

    long savePad(Pad pad);

    Long getPadCount();

    List<Pad> getAllPads();

    Pad getPadById(Long person);

    Pad getPadWithNotes(long id);

    Pad getPadByNameAndOwner(String name, long person);

    void deletePad(Long name);
}
