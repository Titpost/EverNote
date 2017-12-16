package com.epam.evernote.controller;

import com.epam.evernote.controller.Exception.NotFoundException;
import com.epam.evernote.model.Tag;
import com.epam.evernote.service.Interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/person")
public class TagController {

  @Autowired
  private TagService tagService;

  @RequestMapping(value = "/{id}/tags", method = RequestMethod.GET)
  public String getAllTags(Long personId, Model model) {
    model.addAttribute("allPads", tagService.getAllTags());
    return "tags";
  }

  @RequestMapping(value = "/{id}/tag/{tagName}", method = RequestMethod.GET)
  public String getTag(Long id, String name, Model model) throws NotFoundException {
    Tag tag = tagService.getTagByOwnerAndName(id, name);
    if (tag == null) {
      throw new NotFoundException(Tag.class, id);
    }
    model.addAttribute("tag", tagService.getTagByOwnerAndName(id, name));
    return "tag";
  }

  @RequestMapping(value = "/{id}/tag/{tagName}", method = RequestMethod.POST)
  public void addTag(@ModelAttribute("Tag") Tag tag,
      ModelMap model) {
    tagService.saveTag(tag);
  }

  @RequestMapping(value = "/{id}/tag/{tagName}", method = RequestMethod.DELETE)
  public void deleteTag(Long id, String name) throws NotFoundException {
    Tag tag = tagService.getTagByOwnerAndName(id, name);
    if (tag == null) {
      throw new NotFoundException(Tag.class, id);
    }
    tagService.deleteTag(name);
  }

}
