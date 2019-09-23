package com.tomdaly.hotel.data.repository;

import com.tomdaly.hotel.data.entity.Guest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, Long> {
  Guest findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);
}
