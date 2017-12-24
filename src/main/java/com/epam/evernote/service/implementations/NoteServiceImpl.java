package com.epam.evernote.service.implementations;


import com.epam.evernote.dao.JdbcTemplateNoteDao;
import com.epam.evernote.model.Note;
import com.epam.evernote.service.interfaces.NoteService;
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
    public long getPadNoteCount(long pad) {
        return noteDao.getNoteCount(pad);
    }

    @Override
    public List<Note> getAllNotes() {
        return noteDao.loadAll();
    }

    @Override
    public List<Note> getPadNotes(long pad) {
        return noteDao.loadAll(pad);
    }

    @Override
    public List<Note> getAllNotes(long person) {
        return noteDao.loadAll(person);
    }

    @Override
    public Note getNoteById(long id) {
        return noteDao.load(id);
    }

    @Override
    public Note getNoteWithTags(long id) {
        return noteDao.loadWithTags(id);
    }

    @Override
    public boolean exists(Note note) {
        return noteDao.exists(note);
    }

    @Override
    public void updateName(long id, String name) {
        noteDao.updateName(id, name);
    }

    @Override
    public void deleteNote(long id) {
        noteDao.delete(id);
    }
}
