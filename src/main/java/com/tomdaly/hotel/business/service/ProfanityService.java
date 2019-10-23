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
import java.util.Optional;

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
      this.profanitySetRepository.save(profanitySet);
      return profanitySet;
    } catch (DataIntegrityViolationException e) {
      return profanitySet;
    }
  }

  @Loggable
  public boolean containsProfanityInSet(String stringToCheck, ProfanitySet profanitySet) {
    for (Profanity profanity : profanitySet.getProfanities()) {
      if (stringToCheck.equals(profanity.getWord())) {
        return true;
      }
    }
    return false;
  }

  public ProfanitySet updateProfanitySetFromRepository(ProfanitySet profanitySet) {
    List<Long> profanityIdsList =
        profanitySetWordsRepository.findProfanityIdsByProfanitySetId(
            String.valueOf(profanitySet.getId()));
    profanitySet.clearProfanities();
    for (long profanityId : profanityIdsList) {
      Optional<Profanity> profanityOptional = profanityRepository.findById(profanityId);
      if (profanityOptional.isPresent()) {
        Profanity profanity = profanityOptional.get();
        profanitySet.addProfanity(profanity);
      }
    }
    return profanitySet;
  }
}
