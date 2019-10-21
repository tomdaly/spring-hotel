package com.tomdaly.hotel.business.service;

import com.tomdaly.hotel.aspect.Loggable;
import com.tomdaly.hotel.data.entity.Guest;
import com.tomdaly.hotel.data.entity.Profanity;
import com.tomdaly.hotel.data.repository.GuestRepository;
import com.tomdaly.hotel.data.repository.ProfanityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.objenesis.SpringObjenesis;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProfanityService {
  private final ProfanityRepository profanityRepository;

  @Autowired
  public ProfanityService(ProfanityRepository profanityRepository) {
    this.profanityRepository = profanityRepository;
  }

  @Loggable
  public Profanity addProfanity(String word) {
    Profanity profanity = this.profanityRepository.findByWord(word);
    if (profanity == null) {
      profanity = new Profanity(word);
      this.profanityRepository.save(profanity);
    }
    return profanity;
  }
}
