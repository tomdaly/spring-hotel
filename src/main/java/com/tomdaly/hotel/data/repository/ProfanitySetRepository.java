package com.tomdaly.hotel.data.repository;

import com.tomdaly.hotel.data.entity.ProfanitySet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfanitySetRepository extends CrudRepository<ProfanitySet, Long> {
    ProfanitySet findByName(String name);
}
