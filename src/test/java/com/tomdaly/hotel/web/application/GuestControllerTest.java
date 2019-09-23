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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(GuestController.class)
public class GuestControllerTest {

  @MockBean private GuestService guestService;
  @Autowired private MockMvc mockMvc;

  @Test
  public void testFindGuest_shouldReturnCorrectGuestAsAttribute() throws Exception {
    Guest mockGuest = createMockGuest();

    given(guestService.findGuest("Foo", "Bar")).willReturn(mockGuest);
    this.mockMvc
        .perform(get("/guests/find?firstName=Foo&lastName=Bar"))
        .andExpect(status().isFound())
        .andExpect(flash().attribute("guest", mockGuest));
  }

  @Test
  public void testAddGuest_shouldReturnNewGuestAsAttribute() throws Exception {
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
  public void testDeleteGuest_shouldFailWithNonexistentGuest() throws Exception {
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
  public void testDeleteGuest_shouldPassWithExistingGuest() throws Exception {
    given(guestService.deleteGuest("Foo", "Bar")).willReturn("Guest 'Foo Bar' deleted");
    this.mockMvc
        .perform(
            post("/guests/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity("firstName", "Foo", "lastName", "Bar")))
        .andExpect(status().isFound())
        .andExpect(flash().attribute("deleteMessage", "Guest 'Foo Bar' deleted"));
  }
}
