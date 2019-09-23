package com.tomdaly.hotel.data.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "RESERVATION")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "RESERVATION_ID")
  private long id;

  @Column(name = "ROOM_ID")
  private long roomId;

  @Column(name = "GUEST_ID")
  private long guestId;

  @Column(name = "RES_DATE")
  private Date date;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "Reservation{"
        + "id="
        + id
        + ", roomId="
        + roomId
        + ", guestId="
        + guestId
        + ", date="
        + date
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reservation that = (Reservation) o;
    return id == that.id
        && roomId == that.roomId
        && guestId == that.guestId
        && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, roomId, guestId, date);
  }
}
