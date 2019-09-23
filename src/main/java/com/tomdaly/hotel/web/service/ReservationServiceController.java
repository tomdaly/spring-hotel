package com.tomdaly.hotel.web.service;

import com.tomdaly.hotel.business.domain.RoomReservation;
import com.tomdaly.hotel.business.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ReservationServiceController {
  @Autowired private ReservationService reservationService;

  @RequestMapping(method = RequestMethod.GET, value = "/reservations/{date}")
  public List<RoomReservation> getAllReservationsForDate(
      @PathVariable(value = "date") String dateString) {
    return this.reservationService.getRoomReservationsForDate(dateString);
  }

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/reservations/add/{firstName}/{lastName}/{date}")
  public RoomReservation addReservation(
      @PathVariable(value = "firstName") String firstName,
      @PathVariable(value = "lastName") String lastName,
      @PathVariable(value = "date") String dateString) {
    return this.reservationService.addReservation(firstName, lastName, dateString);
  }
}
