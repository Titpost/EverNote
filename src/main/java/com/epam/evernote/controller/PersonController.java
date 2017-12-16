package com.epam.evernote.controller;

import com.epam.evernote.controller.Exception.NotFoundException;
import com.epam.evernote.model.Person;
import com.epam.evernote.service.Interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/person")
public class PersonController {

  @Autowired
  private PersonService personService;

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public String getAllPerson(Model model) {
    model.addAttribute("all", personService.getAllPersons());
    return "all";
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String getPerson(Long id, Model model) throws NotFoundException {
    Person person = personService.getPersonById(id);
    if (person == null) {
      throw new NotFoundException(Person.class, id);
    }
    model.addAttribute("person", person);
    return "person";
  }

  @RequestMapping(method = RequestMethod.POST)
  public void addPerson(@ModelAttribute("Person") Person person,
      ModelMap model) {
    personService.savePerson(person);
  }

  @RequestMapping(method = RequestMethod.PUT)
  public void updateName(Long id, String name) throws NotFoundException {
    Person person = personService.getPersonById(id);
    if (person == null) {
      throw new NotFoundException(Person.class, id);
    }
    personService.updateName(id, name);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void deletePerson(Long id) throws NotFoundException {
    Person person = personService.getPersonById(id);
    if (person == null) {
      throw new NotFoundException(Person.class, id);
    }
    personService.deletePerson(id);
  }


}
