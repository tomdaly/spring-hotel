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
  private final String name;

  @Column(name = "ROOM_NUMBER")
  private final String number;

  @Column(name = "BED_INFO")
  private String bedInfo;

  public Room() {
    this.name = "";
    this.number = "";
  }

  public Room(String name, String number) {
    this.name = name;
    this.number = number;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getNumber() {
    return number;
  }

  public String getBedInfo() {
    return bedInfo;
  }

  public void setBedInfo(String bedInfo) {
    this.bedInfo = bedInfo;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Room)) return false;
    Room that = (Room) o;
    return Objects.equals(name, that.name) && Objects.equals(number, that.number);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, number);
  }
}
