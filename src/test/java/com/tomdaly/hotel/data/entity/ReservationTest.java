package com.tomdaly.hotel.data.entity;

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
public class ReservationTest {

  @Test
  public void testNoArgConstructor() {
    Reservation reservation = new Reservation();
    assertThat(reservation.getId(), is(equalTo(0L)));
    assertThat(reservation.getRoomId(), is(equalTo(0L)));
    assertThat(reservation.getGuestId(), is(equalTo(0L)));
    assertThat(reservation.getDate(), is(equalTo(java.sql.Date.valueOf(LocalDate.now()))));
  }

  @Test
  public void testArgConstructor() {
    Reservation reservation =
        new Reservation(1, 1, java.sql.Date.valueOf(LocalDate.parse("2019-01-01")));
    assertThat(reservation.getId(), is(equalTo(0L)));
    assertThat(reservation.getRoomId(), is(equalTo(1L)));
    assertThat(reservation.getGuestId(), is(equalTo(1L)));
    assertThat(
        reservation.getDate(), is(equalTo(java.sql.Date.valueOf(LocalDate.parse("2019-01-01")))));
  }

  @Test
  public void testEqualsHashcode() {
    EqualsVerifier.forClass(Reservation.class)
        .withOnlyTheseFields("roomId", "guestId", "date")
        .verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(Reservation.class).verify();
  }
}
