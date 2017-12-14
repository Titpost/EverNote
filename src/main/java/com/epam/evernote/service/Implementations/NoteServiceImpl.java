package com.epam.evernote.service.Implementations;


import com.epam.evernote.dao.JdbcTemplateNoteDao;
import com.epam.evernote.model.Note;
import com.epam.evernote.model.Pad;
import com.epam.evernote.service.Interfaces.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired(required = false)
    private JdbcTemplateNoteDao noteDao;

    @Override
    public long saveNote(Note note) {
        return noteDao.save(note);
    }

    @Override
    public long getNoteCount() {
        return noteDao.getNoteCount();
    }

    @Override
    public List<Note> getAllNotes() {
        return noteDao.loadAll();
    }

    @Override
    public Note getNoteById(long id) {
        return noteDao.load(id);
    }

    @Override
    public Note getNoteByOwnerAndName(Long person, String name) {
        return noteDao.findNoteByOwnerAndName(person, name);
    }

    @Override
    public void deleteNote(long id) {
        noteDao.delete(id);
    }
}
