package com.tomdaly.hotel.web.application;

import com.tomdaly.hotel.business.domain.RoomReservation;
import com.tomdaly.hotel.business.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}
