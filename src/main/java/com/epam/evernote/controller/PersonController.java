package com.epam.evernote.controller;

import com.epam.evernote.model.Person;
import com.epam.evernote.service.Interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController extends Controller {

    @Autowired
    private PersonService personService;

    // =========================================== Get All Persons ==========================================

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getAll() {
        LOG.info("getting all persons");
        List<Person> persons = personService.getAllPersons();

        if (persons == null || persons.isEmpty()) {
            LOG.info("no persons found");
            return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(persons, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Get Person By ID =========================================

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> get(@PathVariable("id") int id) {
        LOG.info("getting person with id: {}", id);
        Person person = personService.getPersonById(id);

        if (person == null) {
            LOG.info("person with id {} not found", id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(person, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Create New Person ========================================

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Person person,
                                       UriComponentsBuilder ucBuilder) {
        LOG.info("creating new person: {}", person);

        if (personService.exists(person)) {
            LOG.info("a person with id " + person.getId() + " already exists");
            return new ResponseEntity<>(responseHeaders, HttpStatus.CONFLICT);
        }

        personService.savePerson(person);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/person/{id}")
                .buildAndExpand(person.getId())
                .toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // =========================================== Update Existing Person ===================================

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> update(@PathVariable int id,
                                         @RequestBody Person person) {
        LOG.info("updating person: {}", person);
        Person currentUser = personService.getPersonById(id);

        if (currentUser == null) {
            LOG.info("Person with id {} not found", id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        currentUser.setId(person.getId());
        currentUser.setName(person.getName());

        personService.updateName(1, person.getName());
        return new ResponseEntity<>(currentUser, responseHeaders, HttpStatus.OK);
    }

    // =========================================== Delete Person ============================================

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        LOG.info("deleting person with id: {}", id);
        Person person = personService.getPersonById(id);

        if (person == null) {
            LOG.info("Unable to delete. Person with id {} not found", id);
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        personService.deletePerson(id);
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }
}
