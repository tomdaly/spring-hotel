package com.tomdaly.hotel.web.application;

import com.tomdaly.hotel.business.domain.RoomReservation;
import com.tomdaly.hotel.business.service.ReservationService;
import com.tomdaly.hotel.data.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationController {

  @Autowired private ReservationService reservationService;

  @RequestMapping(method = RequestMethod.GET)
  public String getReservations(
      @RequestParam(value = "date", required = false) String dateString, Model model) {
    List<RoomReservation> roomReservationList =
        this.reservationService.getRoomReservationsForDate(dateString);
    model.addAttribute("roomReservations", roomReservationList);

    return "reservations";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/add")
  public String addReservationGet(Model model) {
    return "reservations_add";
  }

  @RequestMapping(method = RequestMethod.POST, value = "/add")
  public String addReservationPost(
      Model model,
      final RedirectAttributes redirectAttributes,
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName,
      @RequestParam("date") String dateString) {
    RoomReservation newReservation =
        this.reservationService.addReservation(firstName, lastName, dateString);
    redirectAttributes.addFlashAttribute("reservation", newReservation);

    return "redirect:/reservations/add/result";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/add/result")
  public String addReservationResultGet(Model model) {
    return "reservations_add_result";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/find")
  public String getReservationsForGuest(Model model) {
    return "reservations_find";
  }

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/find",
      params = {"firstName", "lastName"})
  public String getReservationsForGuestGet(
      final RedirectAttributes redirectAttributes,
      Model model,
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName) {
    List<RoomReservation> roomReservationList =
        this.reservationService.getRoomReservationsForGuest(firstName, lastName);
    redirectAttributes.addFlashAttribute("roomReservations", roomReservationList);

    return "redirect:/reservations/find/result";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/find/result")
  public String getReservationsForGuestResultGet(Model model) {
    return "reservations_find_result";
  }

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/delete",
      params = {"roomId", "guestId", "date"})
  public String deleteReservationPost(
      final RedirectAttributes redirectAttributes,
      Model model,
      @RequestParam(value = "roomId") String roomIdStr,
      @RequestParam(value = "guestId") String guestIdStr,
      @RequestParam(value = "date") String dateStr) {
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

    redirectAttributes.addFlashAttribute("deleteMessage", deleteMessage);
    return "redirect:/reservations/find";
  }
}
