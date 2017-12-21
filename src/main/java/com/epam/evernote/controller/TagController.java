package com.epam.evernote.controller;

import com.epam.evernote.model.Tag;
import com.epam.evernote.service.Interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/person/{personId}/tag")
public class TagController extends Controller {

    @Autowired
    private TagService tagService;

    // =========================================== Get All Tags ==========================================

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Tag>> getAll(@PathVariable("personId") long personId) {
        LOG.info("getting all tags for person");
        List<Tag> tags = tagService.getAllTags(personId);

        if (tags == null || tags.isEmpty()) {
            LOG.info("no tags found");
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tags, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Get Tags By Name ======================================
/*
    @RequestMapping(value = "{tagName}", method = RequestMethod.GET)
    public ResponseEntity<List<Tag>> get(@PathVariable("tagName") String tagName,
                                         @PathVariable("personId") long personId) {
        LOG.info("getting tags with name {} for person", tagName);
        List<Tag> tags = tagService.getAllTagsByName(tagName, personId);

        if (tags == null || tags.isEmpty()) {
            LOG.info("no tags found for person");
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tags, responseHeaders, HttpStatus.OK);
    }
*/
}
