package com.tomdaly.hotel.data.repository;

import com.tomdaly.hotel.data.entity.ProfanitySetWords;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfanitySetWordsRepository extends CrudRepository<ProfanitySetWords, Long> {
  @Query("SELECT PROFANITY_ID FROM PROFANITYSETWORDS WHERE PROFANITYSET_ID = ?1")
  List<Long> findProfanityIdsByProfanitySetId(String profanitySetId);
}
