package com.epam.evernote.service.interfaces;

import com.epam.evernote.model.Pad;

import java.util.List;

public interface PadService {

    long savePad(Pad pad);

    Long getPadCount();

    List<Pad> getAllPads();

    List<Pad> getAllPads(long person);

    Pad getPadById(long pad);

    Pad getPadWithNotes(long id);

    Pad getPadByIdAndOwner(long id, long person);

    boolean exists(Pad pad);

    void updateName(long id, String name);

    void deletePad(long name);
}
