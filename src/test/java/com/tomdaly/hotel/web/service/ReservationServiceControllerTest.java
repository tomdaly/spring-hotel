package com.tomdaly.hotel.web.service;

import com.tomdaly.hotel.business.domain.RoomReservation;
import com.tomdaly.hotel.business.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.tomdaly.TestUtils.createMockRoomReservation;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ReservationServiceController.class)
public class ReservationServiceControllerTest {
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @MockBean private ReservationService reservationService;
  @Autowired private MockMvc mockMvc;

  @Test
  public void apiGetReservationsForDate() throws Exception {
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
  public void apiAddReservation() throws Exception {
    RoomReservation mockReservation = createMockRoomReservation();

    given(reservationService.addReservation("Foo", "Bar", "2019-01-01"))
        .willReturn(mockReservation);
    this.mockMvc
        .perform(get("/api/reservations/add/Foo/Bar/2019-01-01"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Foo")));
  }
}
