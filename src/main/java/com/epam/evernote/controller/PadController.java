package com.epam.evernote.controller;


import com.epam.evernote.controller.Exception.NotFoundException;
import com.epam.evernote.model.Pad;
import com.epam.evernote.service.Interfaces.PadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/person")
public class PadController {

  @Autowired
  private PadService padService;

  @RequestMapping(value = "/{id}/pads", method = RequestMethod.GET)
  public String getAllPads(Long personId, Model model) {
    model.addAttribute("allPads", padService.getAllPads());
    return "pads";
  }

  @RequestMapping(value = "/{id}/pad/{name}", method = RequestMethod.GET)
  public String getPad(Long id, String name, Model model) throws NotFoundException {
    Pad pad = padService.getPadByNameAndOwner(name, id);
    if (pad == null) {
      throw new NotFoundException(Pad.class, id);
    }
    model.addAttribute("pad", padService.getPadByNameAndOwner(name, id));
    return "pad";
  }

  @RequestMapping(value = "/{id}/pad/{name}", method = RequestMethod.POST)
  public void addPad(@ModelAttribute("Pad") Pad pad,
      ModelMap model) {
    padService.savePad(pad);
  }

  @RequestMapping(value = "/{id}/pad/{name}", method = RequestMethod.DELETE)
  public void deletePad(Long id, String name) throws NotFoundException {
    Pad pad = padService.getPadByNameAndOwner(name, id);
    if (pad == null) {
      throw new NotFoundException(Pad.class, id);
    }
    padService.deletePad(pad.getId());
  }

}
