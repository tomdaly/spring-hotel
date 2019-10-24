package com.tomdaly.hotel.data.repository;

import com.tomdaly.hotel.data.entity.ProfanitySetWords;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfanitySetWordsRepository extends CrudRepository<ProfanitySetWords, Long> {
  @Query("SELECT profanityId FROM ProfanitySetWords WHERE profanitySetId = ?1")
  List<Long> findProfanityIdsByProfanitySetId(Long profanitySetId);
}
