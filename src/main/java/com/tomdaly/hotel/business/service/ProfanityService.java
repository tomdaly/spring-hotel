package com.tomdaly.hotel.business.service;

import com.tomdaly.hotel.aspect.Loggable;
import com.tomdaly.hotel.data.entity.Profanity;
import com.tomdaly.hotel.data.entity.ProfanitySet;
import com.tomdaly.hotel.data.repository.ProfanityRepository;
import com.tomdaly.hotel.data.repository.ProfanitySetRepository;
import com.tomdaly.hotel.data.repository.ProfanitySetWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfanityService {
  private final ProfanityRepository profanityRepository;
  private final ProfanitySetRepository profanitySetRepository;
  private final ProfanitySetWordsRepository profanitySetWordsRepository;
  private List<ProfanitySet> profanitySets;

  @Autowired
  public ProfanityService(
      ProfanityRepository profanityRepository,
      ProfanitySetRepository profanitySetRepository,
      ProfanitySetWordsRepository profanitySetWordsRepository) {
    this.profanityRepository = profanityRepository;
    this.profanitySetRepository = profanitySetRepository;
    this.profanitySetWordsRepository = profanitySetWordsRepository;
  }

  @Loggable
  public ProfanitySet addProfanityToSet(String word, ProfanitySet profanitySet) {
    if (profanitySetRepository.existsById(profanitySet.getId())) {
      Profanity profanity = new Profanity(word);
      profanitySet.addProfanity(profanity);
      profanityRepository.save(profanity);
      profanitySetRepository.save(profanitySet);
      return profanitySet;
    }
    return profanitySet;
  }

  @Loggable
  public ProfanitySet deleteProfanityFromSet(String word, ProfanitySet profanitySet) {
    Profanity profanity = this.profanityRepository.findByWord(word);
    if (profanity == null) {
      return profanitySet;
    }
    try {
      profanitySet.deleteProfanity(profanity);
      this.profanityRepository.delete(profanity);
      this.profanitySetRepository.save(profanitySet);
      return profanitySet;
    } catch (DataIntegrityViolationException e) {
      return profanitySet;
    }
  }

  /*
  @Loggable
  public boolean containsProfanity(String stringToCheck) {
    profanitySet = fillProfanitySetFromRepository(profanityRepository);
    for (Profanity profanity : profanitySet) {
      if (stringToCheck.equals(profanity.getWord())) {
        return true;
      }
    }
    return false;
  }

  private static Set<Profanity> fillProfanitySetFromRepository(ProfanityRepository repository) {
    Iterable<Profanity> iter = repository.findAll();
    return new HashSet<Profanity>((Collection) iter);
  }
   */
}
