package com.epam.evernote.service.Implementations;


import com.epam.evernote.dao.JdbcTemplateNoteDao;
import com.epam.evernote.model.Note;
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
    public List<Note> getAllNotes(long pad) {
        return noteDao.loadAll(pad);
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
        return false;
    }

    @Override
    public void updateName(long id, String name) {

    }

    @Override
    public void deleteNote(long id) {
        noteDao.delete(id);
    }
}
