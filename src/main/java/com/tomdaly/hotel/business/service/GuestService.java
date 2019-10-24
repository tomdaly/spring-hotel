package com.tomdaly.hotel.business.service;

import com.tomdaly.hotel.aspect.Loggable;
import com.tomdaly.hotel.data.entity.Guest;
import com.tomdaly.hotel.data.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
  private final GuestRepository guestRepository;
  private final ProfanityService profanityService;

  @Autowired
  public GuestService(GuestRepository guestRepository, ProfanityService profanityService) {
    this.guestRepository = guestRepository;
    this.profanityService = profanityService;
  }

  @Loggable
  public Guest addGuest(
      String firstName,
      String lastName,
      String email,
      String address,
      String country,
      String state,
      String phoneNumber) {
    Guest guest = findGuest(firstName, lastName);
    if (!(guest.getFirstName().equals(""))) {
      return guest;
    }
    if (!(profanityService.containsProfanity(firstName)
        || profanityService.containsProfanity(lastName))) {
      guest = new Guest(firstName, lastName);
      guest.setEmailAddress(email);
      guest.setAddress(address);
      guest.setCountry(country);
      guest.setState(state);
      guest.setPhoneNumber(phoneNumber);
      this.guestRepository.save(guest);
      return guest;
    } else {
      return new Guest();
    }
  }

  @Loggable
  public Guest findGuest(String firstName, String lastName) {
    Guest guest = this.guestRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);
    return (guest != null) ? guest : new Guest();
  }

  @Loggable
  public String deleteGuest(String firstName, String lastName) {
    Guest guest = findGuest(firstName, lastName);
    if (!(guest.getFirstName().equals(""))) {
      try {
        this.guestRepository.delete(guest);
        return "Guest '" + firstName + " " + lastName + "' deleted";
      } catch (DataIntegrityViolationException e) {
        return "Could not delete guest '"
            + firstName
            + " "
            + lastName
            + "': guest has existing reservation";
      }
    }
    return "Guest not found";
  }
}
