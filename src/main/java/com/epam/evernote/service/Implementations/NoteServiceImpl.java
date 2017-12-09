package com.epam.evernote.service.Implementations;


import com.epam.evernote.dao.JdbcTemplateNoteDao;
import com.epam.evernote.model.Note;
import com.epam.evernote.service.Interfaces.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("noteTemplateRepo")
public class NoteServiceImpl implements NoteService {

    @Autowired(required = false)
    private JdbcTemplateNoteDao noteDao;

    @Override
    public void saveNote(Note note) {
        noteDao.save(note);
    }

    @Override
    public Long getNoteCount() {
        return noteDao.getNoteCount();
    }

    @Override
    public List<Note> getAllNotes() {
        return noteDao.loadAll();
    }

    @Override
    public Note getNoteById(String id) {
        return noteDao.load(id);
    }

    @Override
    public void deleteNote(String id) {
        noteDao.delete(id);
    }
}
