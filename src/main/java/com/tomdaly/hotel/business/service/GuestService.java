package com.tomdaly.hotel.business.service;

import com.tomdaly.hotel.aspect.Loggable;
import com.tomdaly.hotel.data.entity.Guest;
import com.tomdaly.hotel.data.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GuestService {
  private final GuestRepository guestRepository;
  private Set<String> profanitySet;

  @Autowired
  public GuestService(GuestRepository guestRepository) {
    this.guestRepository = guestRepository;
    this.profanitySet = new HashSet<String>();
    this.profanitySet.add("Profanity");
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
    guest = new Guest(firstName, lastName);
    guest.setEmailAddress(email);
    guest.setAddress(address);
    guest.setCountry(country);
    guest.setState(state);
    guest.setPhoneNumber(phoneNumber);

    this.guestRepository.save(guest);

    return guest;
  }

  @Loggable
  public boolean isNameValid(String firstName, String lastName) {
    for (String term : profanitySet) {
      if (firstName.equals(term)) {
        return false;
      }
      if (lastName.equals(term)) {
        return false;
      }
    }
    return true;
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
