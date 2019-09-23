package com.tomdaly.hotel.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "GUEST")
public class Guest {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "GUEST_ID")
  private long id;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "EMAIL_ADDRESS")
  private String emailAddress;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "STATE")
  private String state;

  @Column(name = "PHONE_NUMBER")
  private String phoneNumber;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "Guest{"
        + "id="
        + id
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", emailAddress='"
        + emailAddress
        + '\''
        + ", address='"
        + address
        + '\''
        + ", country='"
        + country
        + '\''
        + ", state='"
        + state
        + '\''
        + ", phoneNumber='"
        + phoneNumber
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Guest guest = (Guest) o;
    return id == guest.id
        && Objects.equals(firstName, guest.firstName)
        && Objects.equals(lastName, guest.lastName)
        && Objects.equals(emailAddress, guest.emailAddress)
        && Objects.equals(address, guest.address)
        && Objects.equals(country, guest.country)
        && Objects.equals(state, guest.state)
        && Objects.equals(phoneNumber, guest.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, firstName, lastName, emailAddress, address, country, state, phoneNumber);
  }
}
