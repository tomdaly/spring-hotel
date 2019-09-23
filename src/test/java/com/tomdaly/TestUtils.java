package com.tomdaly;

import com.tomdaly.hotel.business.domain.RoomReservation;
import com.tomdaly.hotel.data.entity.Guest;
import com.tomdaly.hotel.data.entity.Reservation;
import com.tomdaly.hotel.data.entity.Room;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  // POST form helper function to convert form data into encoded data for POSTing
  public static String buildUrlEncodedFormEntity(String... params) {
    if ((params.length % 2) > 0) {
      throw new IllegalArgumentException("Need to give an even number of parameters");
    }
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < params.length; i += 2) {
      if (i > 0) {
        result.append('&');
      }
      try {
        result
            .append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name()))
            .append('=')
            .append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8.name()));
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }
    return result.toString();
  }

  public static RoomReservation createMockRoomReservation() throws Exception {
    Date date = DATE_FORMAT.parse("2019-01-01");
    RoomReservation mockRoomReservation = new RoomReservation();
    mockRoomReservation.setLastName("Bar");
    mockRoomReservation.setFirstName("Foo");
    mockRoomReservation.setDate(date);
    mockRoomReservation.setGuestId(1);
    mockRoomReservation.setRoomId(1);
    mockRoomReservation.setRoomNumber("J1");
    mockRoomReservation.setRoomName("JUnit Test Room");
    return mockRoomReservation;
  }

  public static Reservation createMockReservation() throws Exception {
    String dateString = "2019-01-01";
    Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
    Reservation mockReservation = new Reservation();
    mockReservation.setRoomId(1);
    mockReservation.setGuestId(1);
    mockReservation.setId(1);
    mockReservation.setDate(new java.sql.Date(testDate.getTime()));
    return mockReservation;
  }

  public static Guest createMockGuest() {
    Guest mockGuest = new Guest();
    mockGuest.setId(1);
    mockGuest.setFirstName("Foo");
    mockGuest.setLastName("Bar");
    mockGuest.setEmailAddress("foo@bar.com");
    mockGuest.setAddress("42 Wallaby Way");
    mockGuest.setCountry("Australia");
    mockGuest.setState("Sydney");
    mockGuest.setPhoneNumber("1234567890");

    return mockGuest;
  }

  public static Room createMockRoom() {
    Room mockRoom = new Room();
    mockRoom.setId(1);
    mockRoom.setBedInfo("Q");
    mockRoom.setNumber("J1");
    mockRoom.setName("JUnit Test Room");
    return mockRoom;
  }
}