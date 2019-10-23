package com.tomdaly.hotel.business.service;

import com.tomdaly.hotel.data.entity.Profanity;
import com.tomdaly.hotel.data.entity.ProfanitySet;
import com.tomdaly.hotel.data.repository.ProfanityRepository;
import com.tomdaly.hotel.data.repository.ProfanitySetRepository;
import com.tomdaly.hotel.data.repository.ProfanitySetWordsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProfanityServiceTest {

  @MockBean private ProfanityRepository profanityRepository;
  @MockBean private ProfanitySetRepository profanitySetRepository;
  @MockBean private ProfanitySetWordsRepository profanitySetWordsRepository;
  private ProfanityService profanityService;

  @Before
  public void before() {
    profanityService =
        new ProfanityService(
            profanityRepository, profanitySetRepository, profanitySetWordsRepository);
  }

  @Test
  public void testAddProfanityToSet_givenNewWordAndExistingSet_shouldReturnSetWithNewProfanity() {
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    given(profanitySetRepository.existsById(testProfanitySet.getId())).willReturn(true);
    ProfanitySet returnedProfanitySet =
        profanityService.addProfanityToSet("foobar", testProfanitySet);
    ProfanitySet expectedProfanitySet = new ProfanitySet("test");
    expectedProfanitySet.addProfanity(new Profanity("foobar"));
    assertThat(returnedProfanitySet, is(equalTo(expectedProfanitySet)));
  }

  @Test
  public void
      testAddProfanityToSet_givenNewWordAndNonexistentSet_shouldReturnSetWithoutNewProfanity() {
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    given(profanitySetRepository.existsById(testProfanitySet.getId())).willReturn(false);
    ProfanitySet returnedProfanitySet =
        profanityService.addProfanityToSet("foobar", testProfanitySet);
    ProfanitySet expectedProfanitySet = new ProfanitySet("test");
    assertThat(returnedProfanitySet, is(equalTo(expectedProfanitySet)));
  }

  /*
  @Test
  public void testDeleteProfanityFromSet_givenExistingProfanityAndExistingSet_shouldReturnSetWithoutWord() {
    Profanity mockProfanity = new Profanity("foobar");
    ProfanitySet mockProfanitySet = new ProfanitySet("test");
    given(profanityRepository.findByWord("foobar")).willReturn(mockProfanity);
    doNothing().when(profanityRepository).delete(mockProfanity);
    assertThat(profanityService.deleteProfanity("foobar"), is(equalTo(true)));
  }

  @Test
  public void testDeleteProfanity_givenNonexistentProfanity_shouldReturnFalse() {
    Profanity mockProfanity = new Profanity("foobar");
    given(profanityRepository.findByWord("foobar")).willReturn(null);
    willThrow(DataIntegrityViolationException.class)
        .given(profanityRepository)
        .delete(mockProfanity);
    assertThat(profanityService.deleteProfanity("foobar"), is(equalTo(false)));
  }

  /*
  @Test
  public void testStaticContainsProfanity_givenWordMatchingProfanity_shouldReturnTrue() {
    Set<Profanity> mockProfanitySet = new HashSet<>();
    mockProfanitySet.add(new Profanity("foobar"));
    given(profanityRepository.findAll()).willReturn(mockProfanitySet);
    assertThat(profanityService.containsProfanity("foobar"), is(true));
  }

  @Test
  public void testStaticContainsProfanity_givenWordNotMatchingProfanity_shouldReturnFalse() {
    Set<Profanity> mockProfanitySet = new HashSet<>();
    mockProfanitySet.add(new Profanity("foobar"));
    given(profanityRepository.findAll()).willReturn(mockProfanitySet);
    assertThat(profanityService.containsProfanity("noProfanity"), is(false));
  }
   */
}
