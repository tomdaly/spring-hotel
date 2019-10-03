package com.tomdaly.hotel.data.repository;

import com.tomdaly.hotel.data.entity.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
  List<Reservation> findByDate(java.sql.Date date);

  List<Reservation> findByGuestId(long guestId);

  boolean existsByRoomIdAndGuestIdAndDate(long roomId, long guestId, java.sql.Date date);
}
