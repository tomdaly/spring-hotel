package com.tomdaly.hotel.web.application;

import com.tomdaly.hotel.business.service.GuestService;
import com.tomdaly.hotel.data.entity.Guest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.tomdaly.TestUtils.buildUrlEncodedFormEntity;
import static com.tomdaly.TestUtils.createMockGuest;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(
    controllers = {GuestController.class},
    secure = false)
public class GuestControllerTest {

  @MockBean private GuestService guestService;
  @Autowired private MockMvc mockMvc;

  @Test
  public void testFindGuestGet_shouldReturnCorrectGuestAsAttribute() throws Exception {
    Guest mockGuest = createMockGuest();

    given(guestService.findGuest("Foo", "Bar")).willReturn(mockGuest);
    this.mockMvc
        .perform(get("/guests/find?firstName=Foo&lastName=Bar"))
        .andExpect(status().isFound())
        .andExpect(flash().attribute("guest", mockGuest));
  }

  @Test
  public void testAddGuestPost_shouldReturnNewGuestAsAttribute() throws Exception {
    Guest mockGuest = createMockGuest();

    given(
            guestService.addGuest(
                "Foo", "Bar", "foo@bar.com", "42 Wallaby Way", "Australia", "Sydney", "4445556789"))
        .willReturn(mockGuest);
    this.mockMvc
        .perform(
            post("/guests/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(
                    buildUrlEncodedFormEntity(
                        "firstName",
                        "Foo",
                        "lastName",
                        "Bar",
                        "email",
                        "foo@bar.com",
                        "address",
                        "42 Wallaby Way",
                        "country",
                        "Australia",
                        "state",
                        "Sydney",
                        "phoneNumber",
                        "4445556789")))
        .andExpect(status().isFound())
        .andExpect(flash().attribute("guest", mockGuest));
  }

  @Test
  public void testDeleteGuestPost_shouldFailWithNonexistentGuest() throws Exception {
    given(guestService.deleteGuest("Foo", "Bar")).willReturn("Guest not found");
    this.mockMvc
        .perform(
            post("/guests/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("firstName", "Foo", "lastName", "Bar")))
        .andExpect(status().isFound())
        .andExpect(flash().attribute("deleteMessage", "Guest not found"));
  }

  @Test
  public void testDeleteGuestPost_shouldPassWithExistingGuest() throws Exception {
    given(guestService.deleteGuest("Foo", "Bar")).willReturn("Guest 'Foo Bar' deleted");
    this.mockMvc
        .perform(
            post("/guests/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("firstName", "Foo", "lastName", "Bar")))
        .andExpect(status().isFound())
        .andExpect(flash().attribute("deleteMessage", "Guest 'Foo Bar' deleted"));
  }

  @Test
  public void testWelcomePage_shouldReturnCorrectHtmlPage() throws Exception {
    this.mockMvc
        .perform(get("/guests/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Welcome to the Guests Page")));
  }

  @Test
  public void testFindGuest_shouldReturnCorrectHtmlPage() throws Exception {
    this.mockMvc
        .perform(get("/guests/find/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Find a Guest")));
  }

  @Test
  public void testFindGuestResultGet_shouldReturnCorrectHtmlPage() throws Exception {
    this.mockMvc
        .perform(get("/guests/find/result/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Result")));
  }

  @Test
  public void testAddGuestGet_shouldReturnCorrectHtmlPage() throws Exception {
    this.mockMvc
        .perform(get("/guests/add/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Create a Guest")));
  }

  @Test
  public void testAddGuestResultGet_shouldReturnCorrectHtmlPage() throws Exception {
    this.mockMvc
        .perform(get("/guests/add/result/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Result")));
  }

  @Test
  public void testDeleteGuestGet_shouldReturnCorrectHtmlPage() throws Exception {
    this.mockMvc
        .perform(get("/guests/delete/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Delete a Guest")));
  }

  @Test
  public void testDeleteGuestGetPost_shouldReturnCorrectHtmlPage() throws Exception {
    this.mockMvc
        .perform(get("/guests/delete/result/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Result")));
  }
}
