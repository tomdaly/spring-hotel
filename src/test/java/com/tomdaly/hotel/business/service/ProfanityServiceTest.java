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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;

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
  public void testAddProfanityToSet_givenNewWordAndNonexistentSet_shouldReturnUnchangedSet() {
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    given(profanitySetRepository.existsById(testProfanitySet.getId())).willReturn(false);
    ProfanitySet returnedProfanitySet =
        profanityService.addProfanityToSet("foobar", testProfanitySet);
    ProfanitySet expectedProfanitySet = new ProfanitySet("test");
    assertThat(returnedProfanitySet, is(equalTo(expectedProfanitySet)));
  }

  @Test
  public void
      testDeleteProfanityFromSet_givenExistingProfanityAndExistingSet_shouldReturnSetWithoutWord() {
    Profanity mockProfanity = new Profanity("foobar");
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    testProfanitySet.addProfanity(mockProfanity);
    given(profanityRepository.findByWord("foobar")).willReturn(mockProfanity);
    doNothing().when(profanityRepository).delete(mockProfanity);
    ProfanitySet returnedProfanitySet =
        profanityService.deleteProfanityFromSet("foobar", testProfanitySet);
    ProfanitySet expectedProfanitySet = new ProfanitySet("test");
    assertThat(returnedProfanitySet, is(equalTo(expectedProfanitySet)));
  }

  @Test
  public void testDeleteProfanity_givenNonexistentProfanity_shouldReturnUnchangedSet() {
    Profanity mockProfanity = new Profanity("notFoobar");
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    testProfanitySet.addProfanity(mockProfanity);
    given(profanityRepository.findByWord("foobar")).willReturn(null);
    willThrow(DataIntegrityViolationException.class)
        .given(profanityRepository)
        .delete(mockProfanity);
    ProfanitySet returnedProfanitySet =
        profanityService.deleteProfanityFromSet("foobar", testProfanitySet);
    ProfanitySet expectedProfanitySet = testProfanitySet;
    assertThat(returnedProfanitySet, is(equalTo(expectedProfanitySet)));
  }

  @Test
  public void testContainsProfanity_givenWordMatchingProfanity_shouldReturnTrue() {
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    testProfanitySet.addProfanity(new Profanity("foobar"));
    assertThat(profanityService.containsProfanityInSet("foobar", testProfanitySet), is(true));
  }

  @Test
  public void testContainsProfanity_givenWordNotMatchingProfanity_shouldReturnFalse() {
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    testProfanitySet.addProfanity(new Profanity("notFoobar"));
    assertThat(profanityService.containsProfanityInSet("foobar", testProfanitySet), is(false));
  }

  @Test
  public void
      testUpdateProfanitySet_givenValidProfanitySet_shouldReturnProfanitySetWithAllProfanitiesInRepository() {
    Profanity testProfanityOne = new Profanity("one");
    testProfanityOne.setId(1L);
    Profanity testProfanityTwo = new Profanity("two");
    testProfanityTwo.setId(2L);
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    testProfanitySet.addProfanity(testProfanityOne);
    List<Long> testProfanityIdsList = new ArrayList<>();
    testProfanityIdsList.add(1L);
    testProfanityIdsList.add(2L);
    given(profanitySetWordsRepository.findProfanityIdsByProfanitySetId(testProfanitySet.getId()))
        .willReturn(testProfanityIdsList);
    given(profanityRepository.findById(1L)).willReturn(Optional.of(testProfanityOne));
    given(profanityRepository.findById(2L)).willReturn(Optional.of(testProfanityTwo));

    ProfanitySet expectedProfanitySet = new ProfanitySet("test");
    expectedProfanitySet.addProfanity(testProfanityOne);
    expectedProfanitySet.addProfanity(testProfanityTwo);
    ProfanitySet returnedProfanitySet =
        profanityService.updateProfanitySetFromRepository(testProfanitySet);
    assertThat(returnedProfanitySet, is(equalTo(expectedProfanitySet)));
  }
}
