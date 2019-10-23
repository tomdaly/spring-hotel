package com.tomdaly.hotel.web.service;

import com.tomdaly.hotel.business.service.ProfanityService;
import com.tomdaly.hotel.data.entity.Profanity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(
    controllers = {ProfanityServiceController.class},
    secure = false)
public class ProfanityServiceControllerTest {
  @MockBean private ProfanityService profanityService;
  @Autowired private MockMvc mockMvc;

  /*
  @Test
  public void testApiAddProfanity_givenNewWord_shouldReturnProfanityWithCorrectWord()
      throws Exception {
    Profanity mockProfanity = new Profanity("foobar");
    given(profanityService.addProfanity("foobar")).willReturn(mockProfanity);
    this.mockMvc
        .perform(get("/api/profanity/add/foobar"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("foobar")));
  }

  @Test
  public void testApiDeleteProfanity_givenExistingProfanity_shouldReturnTrue() throws Exception {
    given(profanityService.deleteProfanity("foobar")).willReturn(true);
    this.mockMvc
        .perform(get("/api/profanity/delete/foobar"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("true")));
  }

  @Test
  public void testApiDeleteProfanity_givenNonexistentProfanity_shouldReturnFalse()
      throws Exception {
    given(profanityService.deleteProfanity("foobar")).willReturn(false);
    this.mockMvc
        .perform(get("/api/profanity/delete/foobar"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("false")));
  }
  */
}
