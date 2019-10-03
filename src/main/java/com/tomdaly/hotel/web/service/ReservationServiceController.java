package com.tomdaly.hotel.web.service;

import com.tomdaly.hotel.business.domain.RoomReservation;
import com.tomdaly.hotel.business.service.ReservationService;
import com.tomdaly.hotel.data.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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

  @RequestMapping(method = RequestMethod.GET, value = "/reservations/find/{firstName}/{lastName}")
  public List<RoomReservation> getReservationsForGuest(
      @PathVariable(value = "firstName") String firstName,
      @PathVariable(value = "lastName") String lastName) {
    return this.reservationService.getRoomReservationsForGuest(firstName, lastName);
  }

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/reservations/delete/{roomId}/{guestId}/{date}")
  public String deleteReservation(
      @PathVariable(value = "roomId") String roomIdStr,
      @PathVariable(value = "guestId") String guestIdStr,
      @PathVariable(value = "date") String dateStr) {
    String deleteMessage = "";
    try {
      long roomId = Long.parseLong(roomIdStr);
      long guestId = Long.parseLong(guestIdStr);
      java.sql.Date date = java.sql.Date.valueOf(LocalDate.parse(dateStr));
      Reservation reservation = new Reservation(roomId, guestId, date);
      deleteMessage = this.reservationService.deleteReservation(reservation);
    } catch (NumberFormatException e) {
      deleteMessage = "Invalid reservation ID";
      e.printStackTrace();
    }
    return deleteMessage;
  }
}
