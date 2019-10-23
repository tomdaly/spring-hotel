package com.tomdaly.hotel.web.service;

import com.tomdaly.hotel.business.service.ProfanityService;
import com.tomdaly.hotel.data.entity.Profanity;
import com.tomdaly.hotel.data.entity.ProfanitySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ProfanityServiceController {
  @Autowired private ProfanityService profanityService;

  @RequestMapping(method = RequestMethod.GET, value = "/profanity/add/{set}/{word}")
  public ProfanitySet addProfanityToSet(@PathVariable(value = "set") String profanitySetName, @PathVariable(value = "word") String word) {
    ProfanitySet profanitySet;
    for (ProfanitySet profanitySetIter : profanityService.getProfanitySets()) {
      if ((profanitySet = profanitySetIter).getName().equals(profanitySetName)) {
        return this.profanityService.addProfanityToSet(word, profanitySet);
      }
    }
    return new ProfanitySet();
  }

  @RequestMapping(method = RequestMethod.GET, value = "/profanity/delete/{set}/{word}")
  public ProfanitySet deleteProfanityFromSet(@PathVariable(value = "set") String profanitySetName, @PathVariable(value = "word") String word) {
    ProfanitySet profanitySet;
    for (ProfanitySet profanitySetIter : profanityService.getProfanitySets()) {
      if ((profanitySet = profanitySetIter).getName().equals(profanitySetName)) {
        return this.profanityService.deleteProfanityFromSet(word, profanitySet);
      }
    }
    return new ProfanitySet();
  }
}
