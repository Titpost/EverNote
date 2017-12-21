package com.epam.evernote.controller;

import com.epam.evernote.model.Note;
import com.epam.evernote.model.Tag;
import com.epam.evernote.service.Interfaces.NoteService;
import com.epam.evernote.service.Interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/person/{personId}/pad/{padId}/note")
public class NoteController extends Controller {

    @Autowired
    private NoteService noteService;

    @Autowired
    private TagService tagService;

    // =========================================== Get All Notes ==========================================

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Note>> getAll(@PathVariable("padId") long padId) {
        LOG.info("getting all notes for the pad");
        List<Note> notes = noteService.getAllNotes(padId);

        if (notes == null || notes.isEmpty()) {
            LOG.info("no notes found");
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(notes, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Get Note By ID =========================================

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Note> get(@PathVariable("id") long id) {
        LOG.info("getting note with id: {}", id);
        Note note = noteService.getNoteById(id);

        if (note == null) {
            LOG.info("note with id {} not found", id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(note, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Create New Note ========================================

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Note note,
                                       @PathVariable("padId") long padId,
                                       UriComponentsBuilder ucBuilder) {
        LOG.info("creating new note: {}", note);

        if (noteService.exists(note)) {
            LOG.info("a note with id " + note.getId() + " already exists");
            return new ResponseEntity<>(responseHeaders, HttpStatus.CONFLICT);
        }

        noteService.saveNote(note);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/pad/{id}/note/{noteId}")
                .buildAndExpand(padId, note.getId())
                .toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // =========================================== Update Existing Note ===================================

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Note> update(@PathVariable long id,
                                       @RequestBody Note note) {
        LOG.info("updating note: {}", note);
        Note currentNote = noteService.getNoteById(id);

        if (currentNote == null) {
            LOG.info("Note with id {} not found", id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        currentNote.setId(note.getId());
        currentNote.setName(note.getName());

        noteService.updateName(1, note.getName());
        return new ResponseEntity<>(currentNote, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Delete Note ============================================

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        LOG.info("deleting note with id: {}", id);
        Note note = noteService.getNoteById(id);

        if (note == null) {
            LOG.info("Unable to delete. Note with id {} not found", id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        noteService.deleteNote(id);
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }
    // ====================================================================================================





    // =========================================== Get Tags For Note ======================================

    @RequestMapping(value = "{note}/tag", method = RequestMethod.GET)
    public ResponseEntity<List<Tag>> getTags(@PathVariable("note") long note) {
        LOG.info("getting tags for note", note);
        List<Tag> tags = tagService.getAllNoteTags(note);

        if (tags == null || tags.isEmpty()) {
            LOG.info("no tags found for note");
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tags, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Create New Tag ========================================

    @RequestMapping(value = "{note}/tag/{tag}", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@PathVariable("note") long note,
                                       @RequestBody Tag tag,
                                       UriComponentsBuilder ucBuilder) {
        LOG.info("creating new tag: {}", tag);

        tag.setNote(note);
        if (tagService.exists(tag)) {
            LOG.info("a tag with name " + tag.getName() + " already exists");
            return new ResponseEntity<>(responseHeaders, HttpStatus.CONFLICT);
        }

        tagService.saveTag(tag);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/tag/{name}")
                .buildAndExpand(tag.getName())
                .toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // =========================================== Delete Tag ============================================

    @RequestMapping(value = "{note}/tag/{tag}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("note") long note,
                                       @PathVariable("tag") String tagName) {
        LOG.info("deleting tag with name: {}", tagName);
        Tag tag = tagService.findTagByNameAndNote(tagName, note);

        if (tag == null) {
            LOG.info("Unable to delete. Tag with name {} not found", tagName);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        tagService.deleteTag(tagName, note);
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }

}
