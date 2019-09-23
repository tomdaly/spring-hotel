package com.tomdaly.hotel.web.application;

import com.tomdaly.hotel.business.service.GuestService;
import com.tomdaly.hotel.data.entity.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/guests")
public class GuestController {

  @Autowired private GuestService guestService;

  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String welcomePage(Model model) {
    return "guests";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/find")
  public String findGuest(Model model) {
    return "guests_find";
  }

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/find",
      params = {"firstName", "lastName"})
  public String findGuestGet(
      Model model,
      final RedirectAttributes redirectAttributes,
      @RequestParam(value = "firstName") String firstName,
      @RequestParam(value = "lastName") String lastName) {
    Guest guest = this.guestService.findGuest(firstName, lastName);
    redirectAttributes.addFlashAttribute("guest", guest);

    return "redirect:/guests/find/result";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/find/result")
  public String findGuestResultGet(Model model) {
    return "guests_find_result";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/add")
  public String addGuestGet(Model model) {
    model.addAttribute("newGuest", new Guest());

    return "guests_add";
  }

  @RequestMapping(method = RequestMethod.POST, value = "/add")
  public String addGuestPost(
      Model model,
      final RedirectAttributes redirectAttributes,
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName,
      @RequestParam("email") String email,
      @RequestParam("address") String address,
      @RequestParam("country") String country,
      @RequestParam("state") String state,
      @RequestParam("phoneNumber") String phoneNumber) {
    Guest newGuest =
        this.guestService.addGuest(
            firstName, lastName, email, address, country, state, phoneNumber);
    redirectAttributes.addFlashAttribute("guest", newGuest);

    return "redirect:/guests/add/result";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/add/result")
  public String addGuestResultGet(Model model) {
    return "guests_add_result";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/delete")
  public String deleteGuestGet(Model model) {
    return "guests_delete";
  }

  @RequestMapping(method = RequestMethod.POST, value = "/delete")
  public String deleteGuestPost(
      Model model,
      final RedirectAttributes redirectAttributes,
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) {
    redirectAttributes.addFlashAttribute(
        "deleteMessage", this.guestService.deleteGuest(firstName, lastName));
    return "redirect:/guests/delete/result";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/delete/result")
  public String deleteGuestResultGet(Model model) {
    return "guests_delete_result";
  }
}
