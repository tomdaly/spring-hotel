package com.tomdaly.hotel.business.service;

import com.tomdaly.hotel.business.domain.RoomReservation;
import com.tomdaly.hotel.data.entity.Guest;
import com.tomdaly.hotel.data.entity.Reservation;
import com.tomdaly.hotel.data.entity.Room;
import com.tomdaly.hotel.data.repository.GuestRepository;
import com.tomdaly.hotel.data.repository.ReservationRepository;
import com.tomdaly.hotel.data.repository.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.tomdaly.TestUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReservationServiceTest {

  @MockBean private RoomRepository roomRepository;
  @MockBean private GuestRepository guestRepository;
  @MockBean private ReservationRepository reservationRepository;
  private ReservationService reservationService;

  @Before
  public void before() {
    reservationService =
        new ReservationService(roomRepository, guestRepository, reservationRepository);
  }

  @Test
  public void testAddReservation_shouldReturnNewRoomReservationWithCorrectDetails()
      throws Exception {
    Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-01");

    List<Room> mockRoomList = new ArrayList<>();
    Room mockRoomOne = createMockRoom();
    Room mockRoomTwo = createMockRoom();
    mockRoomTwo.setId(2);
    mockRoomTwo.setNumber("J2");
    mockRoomTwo.setName("JUnit Test Room 2");
    mockRoomList.add(mockRoomOne);
    mockRoomList.add(mockRoomTwo);
    List<Reservation> mockReservationList = new ArrayList<>();
    mockReservationList.add(createMockReservation());
    Guest mockGuest = createMockGuest();
    RoomReservation expectedRoomReservation = createMockRoomReservation();
    expectedRoomReservation.setRoomId(2);
    expectedRoomReservation.setRoomNumber("J2");
    expectedRoomReservation.setRoomName("JUnit Test Room 2");

    given(reservationRepository.findByDate(new java.sql.Date(testDate.getTime())))
        .willReturn(mockReservationList);
    given(roomRepository.findAll()).willReturn(mockRoomList);
    given(guestRepository.findByFirstNameAndLastNameIgnoreCase("Foo", "Bar")).willReturn(mockGuest);
    assertThat(
        reservationService.addReservation("Foo", "Bar", "2019-01-01"),
        is(equalTo(expectedRoomReservation)));
  }

  @Test
  public void testAddReservation_shouldReturnEmptyReservationIfGuestNonexistent() {
    Guest mockGuest = new Guest();
    RoomReservation expectedRoomReservation = new RoomReservation();
    given(guestRepository.findByFirstNameAndLastNameIgnoreCase("Foo", "Bar")).willReturn(mockGuest);
    assertThat(
        reservationService.addReservation("Foo", "Bar", "2019-01-01"),
        is(equalTo(expectedRoomReservation)));
  }

  @Test
  public void testAddReservation_shouldReturnEmptyReservationIfNoRoomsAvailable() throws Exception {
    Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-01");

    List<Room> mockRoomList = new ArrayList<>();
    mockRoomList.add(createMockRoom());
    List<Reservation> mockReservationList = new ArrayList<>();
    mockReservationList.add(createMockReservation());
    Guest mockGuest = createMockGuest();
    RoomReservation expectedRoomReservation = new RoomReservation();

    given(reservationRepository.findByDate(new java.sql.Date(testDate.getTime())))
        .willReturn(mockReservationList);
    given(roomRepository.findAll()).willReturn(mockRoomList);
    given(guestRepository.findByFirstNameAndLastNameIgnoreCase("Foo", "Bar")).willReturn(mockGuest);
    assertThat(
        reservationService.addReservation("Foo", "Bar", "2019-01-01"),
        is(equalTo(expectedRoomReservation)));
  }

  @Test
  public void testGetRoomReservationForDate_shouldReturnCorrectReservationList() throws Exception {
    String dateString = "2019-01-01";
    Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

    List<Reservation> mockReservationList = new ArrayList<>();
    mockReservationList.add(createMockReservation());
    List<Room> mockRoomList = new ArrayList<>();
    mockRoomList.add(createMockRoom());
    Guest mockGuest = createMockGuest();

    List<RoomReservation> mockRoomReservationList = new ArrayList<>();
    mockRoomReservationList.add(createMockRoomReservation());

    given(reservationRepository.findByDate(new java.sql.Date(testDate.getTime())))
        .willReturn(mockReservationList);
    given(roomRepository.findAll()).willReturn(mockRoomList);
    given(guestRepository.findById(1L)).willReturn(Optional.of(mockGuest));
    assertThat(
        reservationService.getRoomReservationsForDate(dateString),
        is(equalTo(mockRoomReservationList)));
  }
}
