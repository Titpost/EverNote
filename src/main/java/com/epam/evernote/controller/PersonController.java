package com.epam.evernote.controller;

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
    public String list(Model model) {
        model.addAttribute("all", personService.getAllPersons());
        return "all";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String person(Long id, Model model) {
        model.addAttribute("id", personService.getPersonById(id));
        return "id";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addStudent(@ModelAttribute("Person") Person person,
                             ModelMap model) {
        model.addAttribute("name",person.getName());
        model.addAttribute("age",person.getPassword());

        return "result";
    }

}
