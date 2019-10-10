package com.tomdaly.hotel.business.domain;

import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoomReservationTest {

  @Test
  public void testNoArgConstructor() {
    RoomReservation roomReservation = new RoomReservation();
    assertThat(roomReservation.getRoomId(), is(equalTo(0L)));
    assertThat(roomReservation.getGuestId(), is(equalTo(0L)));
    assertThat(roomReservation.getDate(), is(LocalDate.now()));
    assertThat(roomReservation.getRoomName(), is(equalTo(null)));
    assertThat(roomReservation.getRoomNumber(), is(equalTo(null)));
    assertThat(roomReservation.getFirstName(), is(equalTo(null)));
    assertThat(roomReservation.getLastName(), is(equalTo(null)));
  }

  @Test
  public void testArgConstructor() {
    RoomReservation roomReservation = new RoomReservation(1, 1, LocalDate.parse("2019-01-01"));
    assertThat(roomReservation.getRoomId(), is(equalTo(1L)));
    assertThat(roomReservation.getGuestId(), is(equalTo(1L)));
    assertThat(roomReservation.getDate(), is(equalTo(LocalDate.parse("2019-01-01"))));
    assertThat(roomReservation.getRoomName(), is(equalTo(null)));
    assertThat(roomReservation.getRoomNumber(), is(equalTo(null)));
    assertThat(roomReservation.getFirstName(), is(equalTo(null)));
    assertThat(roomReservation.getLastName(), is(equalTo(null)));
  }

  @Test
  public void testEqualsHashcode() {
    EqualsVerifier.forClass(RoomReservation.class)
        .withOnlyTheseFields("roomId", "guestId", "date")
        .verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(RoomReservation.class).verify();
  }
}
