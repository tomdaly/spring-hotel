package com.tomdaly.hotel.business.service;

import com.tomdaly.hotel.data.entity.Profanity;
import com.tomdaly.hotel.data.repository.ProfanityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProfanityServiceTest {

  @MockBean private ProfanityRepository profanityRepository;
  private ProfanityService profanityService;

  @Before
  public void before() {
    profanityService = new ProfanityService(profanityRepository);
  }

  @Test
  public void testAddProfanity_shouldReturnNewProfanityWithCorrectWord() {
    Profanity mockProfanity = new Profanity("foobar");
    assertThat(profanityService.addProfanity("foobar"), is(equalTo(mockProfanity)));
  }

  @Test
  public void testStaticContainsProfanity_shouldReturnTrueIfWordContainsProfanity() {
    Set<Profanity> mockProfanitySet = new HashSet<>();
    mockProfanitySet.add(new Profanity("foobar"));
    given(profanityRepository.findAll()).willReturn(mockProfanitySet);
    assertThat(profanityService.containsProfanity("foobar"), is(true));
  }

  @Test
  public void testStaticContainsProfanity_shouldReturnFalseIfWordDoesNotContainProfanity() {
    Set<Profanity> mockProfanitySet = new HashSet<>();
    mockProfanitySet.add(new Profanity("foobar"));
    given(profanityRepository.findAll()).willReturn(mockProfanitySet);
    assertThat(profanityService.containsProfanity("noProfanity"), is(false));
  }
}
