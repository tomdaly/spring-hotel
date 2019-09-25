package com.tomdaly.hotel.web.application;

import com.tomdaly.hotel.business.domain.RoomReservation;
import com.tomdaly.hotel.business.service.ReservationService;
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
import static com.tomdaly.TestUtils.createMockRoomReservation;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

  @MockBean private ReservationService reservationService;
  @Autowired private MockMvc mockMvc;

  @Test
  public void testGetReservations_shouldReturnCorrectReservationsForDate() throws Exception {
    List<RoomReservation> mockReservationList = new ArrayList<>();
    RoomReservation mockReservation = createMockRoomReservation();
    mockReservationList.add(mockReservation);

    given(reservationService.getRoomReservationsForDate("2019-01-01"))
        .willReturn(mockReservationList);
    this.mockMvc
        .perform(get("/reservations?date=2019-01-01"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("JUnit Test")));
  }

  @Test
  public void testAddReservation_shouldReturnCorrectRoomReservation() throws Exception {
    RoomReservation mockReservation = createMockRoomReservation();

    given(reservationService.addReservation("Foo", "Bar", "2019-01-01"))
        .willReturn(mockReservation);
    this.mockMvc
        .perform(
            post("/reservations/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(
                    buildUrlEncodedFormEntity(
                        "firstName", "Foo", "lastName", "Bar", "date", "2019-01-01")))
        .andExpect(status().isFound())
        .andExpect(flash().attribute("reservation", mockReservation));
  }

  @Test
  public void testGetReservations_shouldReturnCorrectHtmlPage() throws Exception {
    this.mockMvc
        .perform(get("/reservations/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Welcome to the Reservations Page")));
  }

  @Test
  public void testAddReservationGet_shouldReturnCorrectHtmlPage() throws Exception {
    this.mockMvc
        .perform(get("/reservations/add/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Add Reservation")));
  }

  @Test
  public void testAddReservationResultGet_shouldReturnCorrectHtmlPage() throws Exception {
    this.mockMvc
        .perform(get("/reservations/add/result"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Result")));
  }
}
