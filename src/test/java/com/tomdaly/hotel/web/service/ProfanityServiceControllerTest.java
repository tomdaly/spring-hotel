package com.tomdaly.hotel.web.service;

import com.tomdaly.hotel.business.service.ProfanityService;
import com.tomdaly.hotel.data.entity.Profanity;
import com.tomdaly.hotel.data.entity.ProfanitySet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.tomdaly.TestUtils.buildUrlEncodedFormEntity;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(
    controllers = {ProfanityServiceController.class},
    secure = false)
public class ProfanityServiceControllerTest {
  @MockBean private ProfanityService profanityService;
  @Autowired private MockMvc mockMvc;

  @Test
  public void
      testApiAddProfanityToSet_givenNewWordAndExistingSet_shouldReturnProfanitySetContainingCorrectProfanity()
          throws Exception {
    Profanity testProfanity = new Profanity("foobar");
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    ProfanitySet expectedProfanitySet = new ProfanitySet("test");
    expectedProfanitySet.addProfanity(testProfanity);

    List<ProfanitySet> profanitySetsList = new ArrayList<>();
    profanitySetsList.add(testProfanitySet);
    given(profanityService.getProfanitySets()).willReturn(profanitySetsList);
    given(profanityService.addProfanityToSet("foobar", testProfanitySet))
        .willReturn(expectedProfanitySet);
    this.mockMvc
        .perform(
            post("/api/profanity/add/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("set", "test", "word", "foobar")))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("foobar")));
  }

  @Test
  public void testApiAddProfanityToSet_givenNewWordAndNonexistentSet_shouldReturnEmptyProfanitySet()
      throws Exception {
    Profanity testProfanity = new Profanity("foobar");
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    ProfanitySet expectedProfanitySet = new ProfanitySet();

    given(profanityService.getProfanitySets()).willReturn(new ArrayList<>());
    given(profanityService.addProfanityToSet("foobar", testProfanitySet))
        .willReturn(expectedProfanitySet);
    this.mockMvc
        .perform(
            post("/api/profanity/add/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("set", "test", "word", "foobar")))
        .andExpect(status().isOk())
        .andExpect(content().string(not(containsString("foobar"))));
  }

  @Test
  public void
      testApiDeleteProfanityFromSet_givenExistingProfanityAndExistingSet_shouldReturnProfanitySetWithoutGivenProfanity()
          throws Exception {
    Profanity testProfanityOne = new Profanity("foobar");
    Profanity testProfanityTwo = new Profanity("notDeleted");
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    testProfanitySet.addProfanity(testProfanityOne);
    testProfanitySet.addProfanity(testProfanityTwo);
    ProfanitySet expectedProfanitySet = new ProfanitySet();
    expectedProfanitySet.addProfanity(testProfanityTwo);
    List<ProfanitySet> profanitySetsList = new ArrayList<>();
    profanitySetsList.add(testProfanitySet);
    given(profanityService.getProfanitySets()).willReturn(profanitySetsList);
    given(profanityService.deleteProfanityFromSet("foobar", testProfanitySet))
        .willReturn(expectedProfanitySet);
    this.mockMvc
        .perform(
            delete("/api/profanity/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("set", "test", "word", "foobar")))
        .andExpect(status().isOk())
        .andExpect(content().string(not(containsString("foobar"))))
        .andExpect(content().string(containsString("notDeleted")));
  }

  @Test
  public void
      testApiDeleteProfanityFromSet_givenExistingProfanityAndNonexistentSet_shouldReturnEmptyProfanitySet()
          throws Exception {
    Profanity testProfanity = new Profanity("foobar");
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    testProfanitySet.addProfanity(testProfanity);
    ProfanitySet expectedProfanitySet = new ProfanitySet();
    given(profanityService.getProfanitySets()).willReturn(new ArrayList<>());
    given(profanityService.deleteProfanityFromSet("foobar", testProfanitySet))
        .willReturn(expectedProfanitySet);
    this.mockMvc
        .perform(
            delete("/api/profanity/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("set", "test", "word", "foobar")))
        .andExpect(status().isOk())
        .andExpect(content().string(not(containsString("foobar"))));
  }

  @Test
  public void testApiGetProfanitySets_givenExistingProfanitySets_shouldReturnListOfProfanitySets()
      throws Exception {
    ProfanitySet testProfanitySetOne = new ProfanitySet("testOne");
    ProfanitySet testProfanitySetTwo = new ProfanitySet("testTwo");
    Profanity testProfanityOne = new Profanity("foo");
    Profanity testProfanityTwo = new Profanity("bar");
    testProfanitySetOne.addProfanity(testProfanityOne);
    testProfanitySetTwo.addProfanity(testProfanityTwo);
    List<ProfanitySet> testProfanitySetList = new ArrayList<>();
    testProfanitySetList.add(testProfanitySetOne);
    testProfanitySetList.add(testProfanitySetTwo);
    given(profanityService.getProfanitySets()).willReturn(testProfanitySetList);
    this.mockMvc
        .perform(get("/api/profanity/sets"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("testOne")))
        .andExpect(content().string(containsString("testTwo")))
        .andExpect(content().string(containsString("foo")))
        .andExpect(content().string(containsString("bar")));
  }

  @Test
  public void testApiCreateProfanitySet_givenValidProfanitySetName_shouldReturnNewProfanitySetWithName() throws Exception {
    ProfanitySet testProfanitySet = new ProfanitySet("test");
    given(profanityService.createProfanitySet("test")).willReturn(testProfanitySet);
    this.mockMvc
        .perform(post("/api/profanity/sets")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content(buildUrlEncodedFormEntity("name", "test")))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("test")));
  }

  @Test
  public void testApiCreateProfanitySet_givenInvalidProfanitySetName_shouldReturnEmptyProfanitySet() throws Exception {
    ProfanitySet testProfanitySet = new ProfanitySet();
    given(profanityService.createProfanitySet("_")).willReturn(testProfanitySet);
    this.mockMvc
            .perform(post("/api/profanity/sets")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content(buildUrlEncodedFormEntity("name", "_")))
            .andExpect(status().isOk())
            .andExpect(content().string(not(containsString("_"))));
  }
}
