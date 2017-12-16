package com.epam.evernote.controller;

import com.epam.evernote.controller.Exception.NotFoundException;
import com.epam.evernote.model.Note;
import com.epam.evernote.service.Interfaces.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/person")
public class NoteController {

  @Autowired
  private NoteService noteService;

  @RequestMapping(value = "/{id}/pad/{padName}/all", method = RequestMethod.GET)
  public String getAllNotes(Long personId, String padName, Model model) {
    model.addAttribute("allNotes", noteService.getAllNotes());
    return "notes";
  }

  @RequestMapping(value = "/{id}/pad/{padName}/note/{noteId}", method = RequestMethod.GET)
  public String getNote(Long id, Long noteId, String name, Model model) throws NotFoundException {
    Note note = noteService.getNoteById(noteId);
    if (note == null) {
      throw new NotFoundException(Note.class, noteId);
    }
    model.addAttribute("note", note);
    return "note";
  }

  @RequestMapping(value = "/{id}/pad/{padName}/note/{noteId}", method = RequestMethod.POST)
  public void addNote(@ModelAttribute("Note") Note note,
      ModelMap model) {
    noteService.saveNote(note);
  }

  @RequestMapping(value = "/{id}/pad/{padName}/note/{noteId}", method = RequestMethod.DELETE)
  public void deleteNote(Long noteId) throws NotFoundException {
    Note note = noteService.getNoteById(noteId);
    if (note == null) {
      throw new NotFoundException(Note.class, noteId);
    }
    noteService.deleteNote(noteId);
  }
}
