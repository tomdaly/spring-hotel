package com.tomdaly.hotel.web.service;

import com.tomdaly.hotel.business.domain.RoomReservation;
import com.tomdaly.hotel.business.service.ReservationService;
import com.tomdaly.hotel.data.entity.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.tomdaly.TestUtils.createMockReservation;
import static com.tomdaly.TestUtils.createMockRoomReservation;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(
    controllers = {ReservationServiceController.class},
    secure = false)
public class ReservationServiceControllerTest {
  @MockBean private ReservationService reservationService;
  @Autowired private MockMvc mockMvc;

  @Test
  public void testApiGetReservationsForDate_givenValidDate_shouldReturnCorrectReservations()
      throws Exception {
    List<RoomReservation> mockReservationList = new ArrayList<>();
    RoomReservation mockReservation = createMockRoomReservation();
    mockReservationList.add(mockReservation);

    given(reservationService.getRoomReservationsForDate("2019-01-01"))
        .willReturn(mockReservationList);
    this.mockMvc
        .perform(get("/api/reservations/2019-01-01"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("JUnit Test")));
  }

  @Test
  public void testApiAddReservation_givenNewReservationDetails_shouldReturnNewReservation()
      throws Exception {
    RoomReservation mockReservation = createMockRoomReservation();
    given(reservationService.addReservation("Foo", "Bar", "2019-01-01"))
        .willReturn(mockReservation);
    this.mockMvc
        .perform(get("/api/reservations/add/Foo/Bar/2019-01-01"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Foo")));
  }

  @Test
  public void
      testApiGetReservationsForGuest_givenValidGuestName_shouldReturnCorrectReservationList()
          throws Exception {
    List<RoomReservation> mockReservationList = new ArrayList<>();
    RoomReservation mockReservation = createMockRoomReservation();
    mockReservationList.add(mockReservation);

    given(reservationService.getRoomReservationsForGuest("Foo", "Bar"))
        .willReturn(mockReservationList);
    this.mockMvc
        .perform(get("/api/reservations/find/Foo/Bar"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("JUnit Test")));
  }

  @Test
  public void testApiDeleteReservation_givenValidReservationDetails_shouldReturnDeletedMessage()
      throws Exception {
    Reservation reservation = createMockReservation();

    given(reservationService.deleteReservation(reservation)).willReturn("Reservation deleted");
    this.mockMvc
        .perform(get("/api/reservations/delete/1/1/2019-01-01"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Reservation deleted")));
  }

  @Test
  public void testApiDeleteReservation_givenInvalidRoomId_shouldReturnInvalidReservationIdMessage()
      throws Exception {
    this.mockMvc
        .perform(get("/api/reservations/delete/invalidId/1/2019-01-01"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Invalid reservation ID")));
  }

  @Test
  public void testApiDeleteReservation_givenInvalidGuestId_shouldReturnInvalidReservationIdMessage()
      throws Exception {
    this.mockMvc
        .perform(get("/api/reservations/delete/1/invalidId/2019-01-01"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Invalid reservation ID")));
  }
}
