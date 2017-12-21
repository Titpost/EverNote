package com.epam.evernote.controller;

import com.epam.evernote.model.Pad;
import com.epam.evernote.service.Interfaces.PadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/person/{personId}/pad")
public class PadController extends Controller {

    @Autowired
    private PadService padService;

    // =========================================== Get All Pads ==========================================

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Pad>> getAll(@PathVariable("personId") long personId) {
        LOG.info("getting all pads for person");
        List<Pad> pads = padService.getAllPads(personId);

        if (pads == null || pads.isEmpty()) {
            LOG.info("no pads found");
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pads, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Get Pad By ID =========================================

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Pad> get(@PathVariable("id") long id,
                                   @PathVariable("personId") long personId) {
        LOG.info("getting pad with id: {}", id);
        Pad pad = padService.getPadByIdAndOwner(id, personId);

        if (pad == null) {
            LOG.info("pad with id {} not found", id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(pad, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Create New Pad ========================================

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Pad pad, UriComponentsBuilder ucBuilder) {
        LOG.info("creating new pad: {}", pad);

        if (padService.exists(pad)) {
            LOG.info("a pad with id " + pad.getId() + " already exists");
            return new ResponseEntity<>(responseHeaders, HttpStatus.CONFLICT);
        }

        padService.savePad(pad);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/pad/{id}")
                .buildAndExpand(pad.getId())
                .toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // =========================================== Update Existing Pad ===================================

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Pad> update(@PathVariable long id,
                                      @RequestBody Pad pad) {
        LOG.info("updating pad: {}", pad);
        Pad currentPad = padService.getPadById(id);

        if (currentPad == null) {
            LOG.info("Pad with id {} not found", id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        currentPad.setId(pad.getId());
        currentPad.setName(pad.getName());

        padService.updateName(1, pad.getName());
        return new ResponseEntity<>(currentPad, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Delete Pad ============================================

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        LOG.info("deleting pad with id: {}", id);
        Pad pad = padService.getPadById(id);

        if (pad == null) {
            LOG.info("Unable to delete. Pad with id {} not found", id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        padService.deletePad(id);
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }
}
