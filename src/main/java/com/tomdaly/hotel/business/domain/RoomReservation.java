package com.tomdaly.hotel.business.domain;

import java.util.Date;
import java.util.Objects;

public class RoomReservation {
  private long roomId;
  private long guestId;
  private String roomName;
  private String roomNumber;
  private String firstName;
  private String lastName;
  private Date date;

  public long getRoomId() {
    return roomId;
  }

  public void setRoomId(long roomId) {
    this.roomId = roomId;
  }

  public long getGuestId() {
    return guestId;
  }

  public void setGuestId(long guestId) {
    this.guestId = guestId;
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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RoomReservation that = (RoomReservation) o;
    return roomId == that.roomId
        && guestId == that.guestId
        && Objects.equals(roomName, that.roomName)
        && Objects.equals(roomNumber, that.roomNumber)
        && Objects.equals(firstName, that.firstName)
        && Objects.equals(lastName, that.lastName)
        && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, guestId, roomName, roomNumber, firstName, lastName, date);
  }
}
