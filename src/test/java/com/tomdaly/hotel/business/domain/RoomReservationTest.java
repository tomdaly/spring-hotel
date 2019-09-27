package com.tomdaly.hotel.business.domain;

import com.jparams.verifier.tostring.ToStringVerifier;
import com.tomdaly.hotel.business.service.GuestService;
import com.tomdaly.hotel.data.entity.Guest;
import com.tomdaly.hotel.data.repository.GuestRepository;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.tomdaly.TestUtils.createMockGuest;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoomReservationTest {

  @Test
  public void testEqualsHashcode() {
    EqualsVerifier.forClass(RoomReservation.class).withOnlyTheseFields("roomId", "guestId", "date").verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(RoomReservation.class).verify();
  }
}
