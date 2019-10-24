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
public class ProfanitySetWordsTest {

  @Test
  public void testNoArgConstructor() {
    ProfanitySetWords profanitySetWords = new ProfanitySetWords();
    assertThat(profanitySetWords.getId(), is(equalTo(0L)));
    assertThat(profanitySetWords.getProfanitySetId(), is(equalTo(0L)));
    assertThat(profanitySetWords.getProfanityId(), is(equalTo(0L)));
  }

  @Test
  public void testArgConstructor() {
    ProfanitySetWords profanitySetWords = new ProfanitySetWords(1L, 1L);
    assertThat(profanitySetWords.getId(), is(equalTo(0L)));
    assertThat(profanitySetWords.getProfanitySetId(), is(equalTo(1L)));
    assertThat(profanitySetWords.getProfanityId(), is(equalTo(1L)));
  }

  @Test
  public void testEqualsHashcode() {
    EqualsVerifier.forClass(ProfanitySetWords.class).verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(ProfanitySetWords.class).verify();
  }
}
