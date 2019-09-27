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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class ReservationService {
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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
      if (bookingGuest == null || bookingGuest.getFirstName() == null) {
      return new RoomReservation();
    }

    Date reservationDate = createDateFromDateString(dateString);
    Room roomToReserve = findAvailableRoom(reservationDate);
    if (roomToReserve.getName() == null) {
      return new RoomReservation();
    }
    RoomReservation roomReservation =
        createRoomReservationForDate(roomToReserve, reservationDate, bookingGuest);
    saveReservation(roomReservation);

    return roomReservation;
  }

  private Room findAvailableRoom(Date reservationDate) {
    Iterable<Room> rooms = this.roomRepository.findAll();
    Iterable<Reservation> reservationsOnDate =
        this.reservationRepository.findByDate(new java.sql.Date(reservationDate.getTime()));
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

  private RoomReservation createRoomReservationForDate(Room room, Date date, Guest guest) {
    RoomReservation newRoomReservation = new RoomReservation(room.getId(), guest.getId(), date);
    newRoomReservation.setRoomName(room.getName());
    newRoomReservation.setRoomNumber(room.getNumber());
    newRoomReservation.setFirstName(guest.getFirstName());
    newRoomReservation.setLastName(guest.getLastName());
    return newRoomReservation;
  }

  private void saveReservation(RoomReservation roomReservation) {
    Reservation reservationToSave = new Reservation(roomReservation.getRoomId(), roomReservation.getGuestId(), new java.sql.Date(roomReservation.getDate().getTime()));
    this.reservationRepository.save(reservationToSave);
  }

  @Loggable
  public List<RoomReservation> getRoomReservationsForDate(String dateString) {
    Date date = createDateFromDateString(dateString);

    Map<Long, RoomReservation> roomReservationMap = createAndFillRoomReservationMap(date);

    // convert map to list to return
    List<RoomReservation> roomReservations = new ArrayList<>();
    for (Long roomId : roomReservationMap.keySet()) {
      roomReservations.add(roomReservationMap.get(roomId));
    }
    return roomReservations;
  }

  private Map<Long, RoomReservation> createAndFillRoomReservationMap(Date date) {
    Map<Long, RoomReservation> roomReservationMap = new HashMap<>();

    Iterable<Room> rooms = this.roomRepository.findAll();
    Iterable<Reservation> reservationsOnDate =
            this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
    if (reservationsOnDate != null) {
      reservationsOnDate.forEach(reservation -> {
          Optional<Guest> guestResponse = this.guestRepository.findById(reservation.getGuestId());
          if (guestResponse.isPresent()) {
              Guest guest = guestResponse.get();
            RoomReservation roomReservation = new RoomReservation(reservation.getRoomId(), guest.getId(), date);
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservationMap.put(reservation.getRoomId(), roomReservation);
          }
      });
    }
    if (rooms != null) {
      rooms.forEach(room -> {
        RoomReservation roomReservation;
          if ((roomReservation = roomReservationMap.get(room.getId())) != null) {
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getNumber());
          }
      });
    }
    return roomReservationMap;
  }

  /*
  private Map<Long, RoomReservation> fillReservationMapWithRoomDetails(
          Map<Long, RoomReservation> roomReservationMap) {
    Iterable<Room> rooms = this.roomRepository.findAll();
    rooms.forEach(
            room -> {
              RoomReservation roomReservation = new RoomReservation();
              roomReservation.setRoomName(room.getName());
              roomReservation.setRoomNumber(room.getNumber());
              roomReservationMap.put(room.getId(), roomReservation);
            });
    return roomReservationMap;
  }


  private Map<Long, RoomReservation> fillReservationMapWithGuestDetails(
      Map<Long, RoomReservation> reservationMap, Date date) {

    Iterable<Reservation> reservations =
        this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
    if (reservations != null) {
      reservations.forEach(
          reservation -> {
            Optional<Guest> guestResponse = this.guestRepository.findById(reservation.getGuestId());
            if (guestResponse.isPresent()) {
              // fills second half of roomReservation with guest details
              Guest guest = guestResponse.get();
              RoomReservation roomReservation = reservationMap.get(reservation.getRoomId());
              roomReservation.setDate(date);
              roomReservation.setFirstName(guest.getFirstName());
              roomReservation.setLastName(guest.getLastName());
              roomReservation.setGuestId(guest.getId());
            }
          });
    }
    return reservationMap;
  }
   */

  private Date createDateFromDateString(String dateString) {
    Date date;
    if (dateString != null) {
      try {
        date = DATE_FORMAT.parse(dateString);
      } catch (ParseException e) {
        date = new Date();
      }
    } else {
      date = new Date(); // assume todays date if no date set
    }
    return date;
  }
}
