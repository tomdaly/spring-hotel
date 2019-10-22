package com.tomdaly.hotel.web.service;

import com.tomdaly.hotel.business.service.ProfanityService;
import com.tomdaly.hotel.data.entity.Profanity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ProfanityServiceController {
  @Autowired private ProfanityService profanityService;

  @RequestMapping(method = RequestMethod.GET, value = "/profanity/add/{word}")
  public Profanity addProfanity(@PathVariable(value = "word") String word) {
    return this.profanityService.addProfanity(word);
  }
}
