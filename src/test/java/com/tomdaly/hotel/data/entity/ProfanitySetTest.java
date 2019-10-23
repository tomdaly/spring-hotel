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
public class ProfanitySetTest {

  @Test
  public void testNoArgConstructor() {
    ProfanitySet profanitySet = new ProfanitySet();
    assertThat(profanitySet.getId(), is(equalTo(0L)));
    assertThat(profanitySet.getName(), is(equalTo("")));
    assertThat(profanitySet.getProfanities().size(), is(equalTo(0)));
  }

  @Test
  public void testArgConstructor() {
    ProfanitySet profanitySet = new ProfanitySet("test");
    assertThat(profanitySet.getId(), is(equalTo(0L)));
    assertThat(profanitySet.getName(), is(equalTo("test")));
    assertThat(profanitySet.getProfanities().size(), is(equalTo(0)));
  }

  @Test
  public void testAddProfanity_givenValidProfanity_shouldAddToSet() {
    ProfanitySet profanitySet = new ProfanitySet("test");
    Profanity testProfanity = new Profanity("foobar");
    profanitySet.addProfanity(testProfanity);
    assertThat(profanitySet.getProfanities().size(), is(equalTo(1)));
  }

  @Test
  public void testClearProfanity_onCall_shouldDeleteAllProfanitiesFromSet() {
    ProfanitySet profanitySet = new ProfanitySet("test");
    Profanity testProfanity = new Profanity("foobar");
    profanitySet.addProfanity(testProfanity);

    profanitySet.clearProfanities();
    assertThat(profanitySet.getProfanities().size(), is(equalTo(0)));
  }

  @Test
  public void testEqualsHashcode() {
    EqualsVerifier.forClass(ProfanitySet.class).verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(ProfanitySet.class).verify();
  }
}
