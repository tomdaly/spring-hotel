package com.tomdaly.hotel.business.service;

import com.tomdaly.hotel.data.entity.Guest;
import com.tomdaly.hotel.data.entity.Profanity;
import com.tomdaly.hotel.data.repository.GuestRepository;
import com.tomdaly.hotel.data.repository.ProfanityRepository;
import com.tomdaly.hotel.data.repository.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.tomdaly.TestUtils.createMockGuest;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

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
}
