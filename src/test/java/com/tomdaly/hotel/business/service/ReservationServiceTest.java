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

import java.time.LocalDate;
import java.util.ArrayList;
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
    LocalDate testDate = LocalDate.parse("2019-01-01");

    List<Room> mockRoomList = new ArrayList<>();
    Room mockRoomOne = createMockRoom();
    Room mockRoomTwo = new Room("JUnit Test Room 2", "J2");
    mockRoomTwo.setBedInfo("Q1");
    mockRoomTwo.setId(2);
    mockRoomList.add(mockRoomOne);
    mockRoomList.add(mockRoomTwo);
    List<Reservation> mockReservationList = new ArrayList<>();
    mockReservationList.add(createMockReservation());
    Guest mockGuest = createMockGuest();
    RoomReservation expectedRoomReservation = new RoomReservation(2, 1, testDate);
    expectedRoomReservation.setFirstName("Foo");
    expectedRoomReservation.setLastName("Bar");
    expectedRoomReservation.setRoomNumber("J2");
    expectedRoomReservation.setRoomName("JUnit Test Room 2");

    given(reservationRepository.findByDate(java.sql.Date.valueOf(testDate)))
        .willReturn(mockReservationList);
    given(roomRepository.findAll()).willReturn(mockRoomList);
    given(guestRepository.findByFirstNameAndLastNameIgnoreCase("Foo", "Bar")).willReturn(mockGuest);
    assertThat(
        reservationService.addReservation("Foo", "Bar", "2019-01-01"),
        is(equalTo(expectedRoomReservation)));
  }

  @Test
  public void testAddReservation_shouldReturnEmptyReservationIfGuestNonexistent() throws Exception {
    LocalDate testDate = LocalDate.parse("2019-01-01");

    Guest mockGuest = new Guest();
    RoomReservation expectedRoomReservation = new RoomReservation(0, 0, LocalDate.now());
    given(guestRepository.findByFirstNameAndLastNameIgnoreCase("Foo", "Bar")).willReturn(mockGuest);
    assertThat(
        reservationService.addReservation("Foo", "Bar", "2019-01-01"),
        is(equalTo(expectedRoomReservation)));
  }

  @Test
  public void testAddReservation_shouldReturnEmptyReservationIfNoRoomsAvailable() throws Exception {
    LocalDate testDate = LocalDate.parse("2019-01-01");

    List<Room> mockRoomList = new ArrayList<>();
    mockRoomList.add(createMockRoom());
    List<Reservation> mockReservationList = new ArrayList<>();
    mockReservationList.add(createMockReservation());
    Guest mockGuest = createMockGuest();
    RoomReservation expectedRoomReservation = new RoomReservation(0, 0, LocalDate.now());

    given(reservationRepository.findByDate(java.sql.Date.valueOf(testDate)))
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
    LocalDate testDate = LocalDate.parse(dateString);

    List<Reservation> mockReservationList = new ArrayList<>();
    mockReservationList.add(createMockReservation());
    List<Room> mockRoomList = new ArrayList<>();
    mockRoomList.add(createMockRoom());
    Guest mockGuest = createMockGuest();

    List<RoomReservation> mockRoomReservationList = new ArrayList<>();
    mockRoomReservationList.add(createMockRoomReservation());

    given(reservationRepository.findByDate(java.sql.Date.valueOf(testDate)))
        .willReturn(mockReservationList);
    given(roomRepository.findAll()).willReturn(mockRoomList);
    given(guestRepository.findById(1L)).willReturn(Optional.of(mockGuest));
    assertThat(
        reservationService.getRoomReservationsForDate(dateString),
        is(equalTo(mockRoomReservationList)));
  }

  @Test
  public void testGetRoomReservationForDate_shouldReturnReservationsForTodayDateIfInvalidDateGiven()
      throws Exception {
    String dateString = "invalid_date";
    LocalDate todaysDate = LocalDate.now();

    List<Reservation> mockReservationList = new ArrayList<>();
    Reservation mockReservation = new Reservation(1, 1, java.sql.Date.valueOf(todaysDate));
    mockReservationList.add(mockReservation);
    List<Room> mockRoomList = new ArrayList<>();
    mockRoomList.add(createMockRoom());
    Guest mockGuest = createMockGuest();

    List<RoomReservation> mockRoomReservationList = new ArrayList<>();
    RoomReservation mockRoomReservation = new RoomReservation(1, 1, todaysDate);
    mockRoomReservation.setRoomNumber("J1");
    mockRoomReservation.setRoomName("JUnit Test Room");
    mockRoomReservation.setFirstName("Foo");
    mockRoomReservation.setLastName("Bar");
    mockRoomReservationList.add(mockRoomReservation);

    given(reservationRepository.findByDate(java.sql.Date.valueOf(todaysDate)))
        .willReturn(mockReservationList);
    given(roomRepository.findAll()).willReturn(mockRoomList);
    given(guestRepository.findById(1L)).willReturn(Optional.of(mockGuest));
    assertThat(
        reservationService.getRoomReservationsForDate(dateString),
        is(equalTo(mockRoomReservationList)));
  }
}
