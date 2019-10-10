package com.tomdaly.hotel.data.entity;

import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoomTest {

  @Test
  public void testNoArgConstructor() {
    Room room = new Room();
    assertThat(room.getId(), is(equalTo(0L)));
    assertThat(room.getName(), is(equalTo("")));
    assertThat(room.getNumber(), is(equalTo("")));
    assertThat(room.getBedInfo(), is(equalTo(null)));
  }

  @Test
  public void testArgConstructor() {
    Room room = new Room("JUnit Test Room", "J1");
    assertThat(room.getId(), is(equalTo(0L)));
    assertThat(room.getName(), is(equalTo("JUnit Test Room")));
    assertThat(room.getNumber(), is(equalTo("J1")));
    assertThat(room.getBedInfo(), is(equalTo(null)));
  }

  @Test
  public void testEqualsHashcode() {
    EqualsVerifier.forClass(Room.class).withOnlyTheseFields("name", "number").verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(Room.class).verify();
  }
}
