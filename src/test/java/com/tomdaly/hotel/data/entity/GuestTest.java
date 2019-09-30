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
public class GuestTest {

  @Test
  public void testNoArgConstructor() {
    Guest guest = new Guest();
    assertThat(guest.getId(), is(equalTo(0L)));
    assertThat(guest.getFirstName(), is(equalTo("")));
    assertThat(guest.getLastName(), is(equalTo("")));
    assertThat(guest.getEmailAddress(), is(equalTo(null)));
    assertThat(guest.getAddress(), is(equalTo(null)));
    assertThat(guest.getCountry(), is(equalTo(null)));
    assertThat(guest.getState(), is(equalTo(null)));
    assertThat(guest.getPhoneNumber(), is(equalTo(null)));
  }

  @Test
  public void testArgConstructor() {
    Guest guest = new Guest("Foo", "Bar");
    assertThat(guest.getFirstName(), is(equalTo("Foo")));
    assertThat(guest.getLastName(), is(equalTo("Bar")));
    assertThat(guest.getId(), is(equalTo(0L)));
    assertThat(guest.getEmailAddress(), is(equalTo(null)));
    assertThat(guest.getAddress(), is(equalTo(null)));
    assertThat(guest.getCountry(), is(equalTo(null)));
    assertThat(guest.getState(), is(equalTo(null)));
    assertThat(guest.getPhoneNumber(), is(equalTo(null)));
  }

  @Test
  public void testEqualsHashcode() {
    EqualsVerifier.forClass(Guest.class).withOnlyTheseFields("firstName", "lastName").verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(Guest.class).verify();
  }
}
