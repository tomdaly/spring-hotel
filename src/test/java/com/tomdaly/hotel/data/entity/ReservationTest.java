package com.tomdaly.hotel.data.entity;

import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReservationTest {

  @Test
  public void testEqualsHashcode() {
    EqualsVerifier.forClass(Reservation.class).withOnlyTheseFields("roomId", "guestId", "date").verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(Reservation.class).verify();
  }
}
