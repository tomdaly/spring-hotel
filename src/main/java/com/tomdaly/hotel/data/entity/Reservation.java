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
  final private long roomId;

  @Column(name = "GUEST_ID")
  final private long guestId;

  @Column(name = "RES_DATE")
  final private Date date;

  public Reservation() {
    this.roomId = 0;
    this.guestId = 0;
    this.date = new Date(new java.util.Date().getTime());
  }

  public Reservation(long roomId, long guestId, Date date) {
    this.roomId = roomId;
    this.guestId = guestId;
    this.date = date;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getRoomId() {
    return roomId;
  }

  public long getGuestId() {
    return guestId;
  }

  public Date getDate() {
    return date;
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
    if (!(o instanceof Reservation)) return false;
    Reservation that = (Reservation) o;
    return
        roomId == that.roomId
        && guestId == that.guestId
        && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, guestId, date);
  }
}
