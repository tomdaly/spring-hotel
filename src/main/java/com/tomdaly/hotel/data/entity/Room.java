package com.tomdaly.hotel.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ROOM")
public class Room {
  @Id
  @Column(name = "ROOM_ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "ROOM_NUMBER")
  private String number;

  @Column(name = "BED_INFO")
  private String bedInfo;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getBedInfo() {
    return bedInfo;
  }

  public void setBedInfo(String bedInfo) {
    this.bedInfo = bedInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Room room = (Room) o;
    return id == room.id
        && Objects.equals(name, room.name)
        && Objects.equals(number, room.number)
        && Objects.equals(bedInfo, room.bedInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, number, bedInfo);
  }

  @Override
  public String toString() {
    return "Room{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", number='"
        + number
        + '\''
        + ", bedInfo='"
        + bedInfo
        + '\''
        + '}';
  }
}
