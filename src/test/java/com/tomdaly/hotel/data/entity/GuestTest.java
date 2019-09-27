package com.tomdaly.hotel.data.entity;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.tomdaly.hotel.business.domain.RoomReservation;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class GuestTest {

  @Test
  public void testEqualsHashcode() {
    EqualsVerifier.forClass(Guest.class).withOnlyTheseFields("firstName", "lastName").verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(Guest.class).verify();
  }
}
