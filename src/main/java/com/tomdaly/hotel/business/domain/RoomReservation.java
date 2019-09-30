package com.tomdaly.hotel.business.domain;

import java.time.LocalDate;
import java.util.Objects;

public class RoomReservation {
  private final long roomId;
  private final long guestId;
  private final LocalDate date;
  private String roomName;
  private String roomNumber;
  private String firstName;
  private String lastName;

  public RoomReservation() {
    this.roomId = 0;
    this.guestId = 0;
    this.date = LocalDate.now();
  }

  public RoomReservation(long roomId, long guestId, LocalDate date) {
    this.roomId = roomId;
    this.guestId = guestId;
    this.date = date;
  }

  public long getRoomId() {
    return roomId;
  }

  public long getGuestId() {
    return guestId;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return "RoomReservation{"
        + "roomId="
        + roomId
        + ", guestId="
        + guestId
        + ", roomName='"
        + roomName
        + '\''
        + ", roomNumber='"
        + roomNumber
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", date="
        + date
        + '}';
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RoomReservation)) return false;
    RoomReservation that = (RoomReservation) o;
    return roomId == that.roomId && guestId == that.guestId && Objects.equals(date, that.date);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(roomId, guestId, date);
  }
}
