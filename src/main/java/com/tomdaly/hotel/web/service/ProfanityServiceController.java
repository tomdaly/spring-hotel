package com.tomdaly.hotel.web.service;

import com.tomdaly.hotel.business.service.ProfanityService;
import com.tomdaly.hotel.data.entity.ProfanitySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ProfanityServiceController {
  @Autowired private ProfanityService profanityService;

  @RequestMapping(method = RequestMethod.POST, value = "/profanity/add")
  public ProfanitySet addProfanityToSet(
      @RequestParam("set") String profanitySetName, @RequestParam("word") String word) {
    ProfanitySet profanitySet;
    for (ProfanitySet profanitySetIter : profanityService.getProfanitySets()) {
      if ((profanitySet = profanitySetIter).getName().equals(profanitySetName)) {
        return this.profanityService.addProfanityToSet(word, profanitySet);
      }
    }
    return new ProfanitySet();
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/profanity/delete")
  public String deleteProfanityFromSet(
      @RequestParam("set") String profanitySetName, @RequestParam("word") String word) {
    ProfanitySet profanitySet;
    for (ProfanitySet profanitySetIter : profanityService.getProfanitySets()) {
      if ((profanitySet = profanitySetIter).getName().equals(profanitySetName)) {
        return this.profanityService.deleteProfanityFromSet(word, profanitySet);
      }
    }
    return "Profanity set '" + profanitySetName + "' not found";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/profanity/sets")
  public List<ProfanitySet> getProfanitySets() {
    return this.profanityService.getProfanitySets();
  }

  @RequestMapping(method = RequestMethod.POST, value = "/profanity/sets")
  public ProfanitySet createProfanitySet(@RequestParam("name") String profanitySetName) {
    return this.profanityService.createProfanitySet(profanitySetName);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/profanity/sets")
  public String deleteProfanitySet(@RequestParam("name") String profanitySetName) {
    return this.profanityService.deleteProfanitySet(profanitySetName);
  }
}
