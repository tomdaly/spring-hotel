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
public class ProfanityTest {

  @Test
  public void testNoArgConstructor() {
    Profanity profanity = new Profanity();
    assertThat(profanity.getId(), is(equalTo(0L)));
    assertThat(profanity.getWord(), is(equalTo("")));
  }

  @Test
  public void testArgConstructor() {
    Profanity profanity = new Profanity("Foo");
    assertThat(profanity.getId(), is(equalTo(0L)));
    assertThat(profanity.getWord(), is(equalTo("Foo")));
  }

  @Test
  public void testEqualsHashcode() {
    EqualsVerifier.forClass(Profanity.class).verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(Profanity.class).verify();
  }
}
