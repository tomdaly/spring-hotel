package com.tomdaly.hotel.data.repository;

import com.tomdaly.hotel.data.entity.Profanity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfanityRepository extends CrudRepository<Profanity, Long> {
  Profanity findByWord(String word);
}
