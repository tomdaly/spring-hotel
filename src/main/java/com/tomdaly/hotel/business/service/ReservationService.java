package com.tomdaly.hotel.business.service;

import com.tomdaly.hotel.aspect.Loggable;
import com.tomdaly.hotel.business.domain.RoomReservation;
import com.tomdaly.hotel.data.entity.Guest;
import com.tomdaly.hotel.data.entity.Reservation;
import com.tomdaly.hotel.data.entity.Room;
import com.tomdaly.hotel.data.repository.GuestRepository;
import com.tomdaly.hotel.data.repository.ReservationRepository;
import com.tomdaly.hotel.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class ReservationService {
  private final RoomRepository roomRepository;
  private final GuestRepository guestRepository;
  private final ReservationRepository reservationRepository;

  @Autowired
  public ReservationService(
      RoomRepository roomRepository,
      GuestRepository guestRepository,
      ReservationRepository reservationRepository) {
    this.roomRepository = roomRepository;
    this.guestRepository = guestRepository;
    this.reservationRepository = reservationRepository;
  }

  @Loggable
  public RoomReservation addReservation(String firstName, String lastName, String dateString) {
    Guest bookingGuest =
        this.guestRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);
    if (bookingGuest == null || bookingGuest.getFirstName().equals("")) {
      return new RoomReservation();
    }

    LocalDate reservationDate = createDateFromDateString(dateString);
    Room roomToReserve = findAvailableRoom(reservationDate);
    if (roomToReserve.getName().equals("")) {
      return new RoomReservation();
    }
    RoomReservation roomReservation =
        createRoomReservationForDate(roomToReserve, reservationDate, bookingGuest);
    saveReservation(roomReservation);

    return roomReservation;
  }

  private Room findAvailableRoom(LocalDate reservationDate) {
    Iterable<Room> rooms = this.roomRepository.findAll();
    Iterable<Reservation> reservationsOnDate =
        this.reservationRepository.findByDate(java.sql.Date.valueOf(reservationDate));
    Iterator<Room> roomIterator = rooms.iterator();

    if (!roomIterator.hasNext()) {
      return new Room();
    }
    Room roomToReserve = roomIterator.next();
    boolean roomFound = false;
    do {
      Room finalRoomToReserve = roomToReserve;
      boolean existingReservation =
          StreamSupport.stream(reservationsOnDate.spliterator(), false)
              .anyMatch(room -> room.getRoomId() == finalRoomToReserve.getId());
      if (existingReservation) {
        if (roomIterator.hasNext()) {
          roomToReserve = roomIterator.next();
        } else {
          break;
        }
      } else {
        roomFound = true;
      }
    } while (!roomFound);
    if (!roomFound) {
      return new Room();
    }
    return roomToReserve;
  }

  private RoomReservation createRoomReservationForDate(Room room, LocalDate date, Guest guest) {
    RoomReservation newRoomReservation = new RoomReservation(room.getId(), guest.getId(), date);
    newRoomReservation.setRoomName(room.getName());
    newRoomReservation.setRoomNumber(room.getNumber());
    newRoomReservation.setFirstName(guest.getFirstName());
    newRoomReservation.setLastName(guest.getLastName());
    return newRoomReservation;
  }

  private void saveReservation(RoomReservation roomReservation) {
    Reservation reservationToSave =
        new Reservation(
            roomReservation.getRoomId(),
            roomReservation.getGuestId(),
            java.sql.Date.valueOf(roomReservation.getDate()));
    this.reservationRepository.save(reservationToSave);
  }

  @Loggable
  public List<RoomReservation> getRoomReservationsForDate(String dateString) {
    LocalDate date = createDateFromDateString(dateString);
    Map<Long, RoomReservation> roomReservationMap = createAndFillRoomReservationMap(date);

    // convert map to list to return
    List<RoomReservation> roomReservations = new ArrayList<>();
    for (Long roomId : roomReservationMap.keySet()) {
      roomReservations.add(roomReservationMap.get(roomId));
    }
    return roomReservations;
  }

  private Map<Long, RoomReservation> createAndFillRoomReservationMap(LocalDate date) {
    Map<Long, RoomReservation> roomReservationMap = new HashMap<>();

    Iterable<Room> rooms = this.roomRepository.findAll();
    Iterable<Reservation> reservationsOnDate =
        this.reservationRepository.findByDate(java.sql.Date.valueOf(date));
    if (reservationsOnDate != null) {
      reservationsOnDate.forEach(
          reservation -> {
            Optional<Guest> guestResponse = this.guestRepository.findById(reservation.getGuestId());
            if (guestResponse.isPresent()) {
              Guest guest = guestResponse.get();
              RoomReservation roomReservation =
                  new RoomReservation(reservation.getRoomId(), guest.getId(), date);
              roomReservation.setFirstName(guest.getFirstName());
              roomReservation.setLastName(guest.getLastName());
              roomReservationMap.put(reservation.getRoomId(), roomReservation);
            }
          });
    }
    if (rooms != null) {
      rooms.forEach(
          room -> {
            RoomReservation roomReservation;
            if ((roomReservation = roomReservationMap.get(room.getId())) != null) {
              roomReservation.setRoomName(room.getName());
              roomReservation.setRoomNumber(room.getNumber());
            }
          });
    }
    return roomReservationMap;
  }

  private LocalDate createDateFromDateString(String dateString) {
    LocalDate date;
    if (dateString != null) {
      try {
        date = LocalDate.parse(dateString);
      } catch (DateTimeParseException e) {
        date = LocalDate.now();
      }
    } else {
      date = LocalDate.now(); // assume todays date if no date set
    }
    return date;
  }

  public List<RoomReservation> getRoomReservationsForGuest(String firstName, String lastName) {
    Guest guest = this.guestRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);
    List<RoomReservation> roomReservationList = new ArrayList<>();
    if (guest != null && !guest.getFirstName().equals("")) {
      Iterable<Reservation> reservations = this.reservationRepository.findByGuestId(guest.getId());
      if (reservations != null) {
        reservations.forEach(
            reservation -> {
              RoomReservation roomReservation =
                  new RoomReservation(
                      reservation.getRoomId(),
                      reservation.getGuestId(),
                      reservation.getDate().toLocalDate());
              Optional<Room> roomOptional = this.roomRepository.findById(reservation.getRoomId());
              if (roomOptional.isPresent()) {
                Room room = roomOptional.get();
                roomReservation.setRoomName(room.getName());
                roomReservation.setRoomNumber(room.getNumber());
                roomReservation.setFirstName(guest.getFirstName());
                roomReservation.setLastName(guest.getLastName());
                roomReservationList.add(roomReservation);
              }
            });
      }
    }
    return roomReservationList;
  }
}
