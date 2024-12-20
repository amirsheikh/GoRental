package com.example.renal.GoRental.service;
import com.example.renal.GoRental.controller.reservation.dto.ReservationRequest;
import com.example.renal.GoRental.model.Car;
import com.example.renal.GoRental.model.Reservation;
import com.example.renal.GoRental.model.User;
import com.example.renal.GoRental.repository.CarRepository;
import com.example.renal.GoRental.repository.ReservationRepository;
import com.example.renal.GoRental.repository.UserRepository;
import com.example.renal.GoRental.security.UserContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserContextUtil userContextUtil;

    @InjectMocks
    private ReservationService reservationService;

    private User user;
    private Car car;
    private ReservationRequest reservationRequest;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        // Set up test data
        startTime = LocalDateTime.of(2024, 12, 21, 10, 0);
        endTime = LocalDateTime.of(2024, 12, 21, 12, 0);
        user = new User();
        user.setUsername("testUser");

        car = new Car();
        car.setId(1L);

        reservationRequest = new ReservationRequest();
        reservationRequest.setCarId(1L);
        reservationRequest.setStartTime(startTime);
        reservationRequest.setEndTime(endTime);

        // Mock the UserContextUtil.getCurrentUser() method
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("testUser", null, new ArrayList<>()); // You can add authorities if needed

        SecurityContextHolder.getContext().setAuthentication(authentication);    }

    @Test
    void testCreateReservationSuccess() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(reservationRepository.existsByCarAndStartTimeBeforeAndEndTimeAfter(
                car, endTime, startTime)).thenReturn(false);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Reservation reservation = reservationService.createReservation(reservationRequest);

        // Assert
        assertNotNull(reservation);
        assertEquals(user, reservation.getUser());
        assertEquals(car, reservation.getCar());
        assertEquals(startTime, reservation.getStartTime());
        assertEquals(endTime, reservation.getEndTime());

        verify(userRepository).findByUsername("testUser");
        verify(carRepository).findById(1L);
        verify(reservationRepository).existsByCarAndStartTimeBeforeAndEndTimeAfter(car, endTime, startTime);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void testCreateReservationCarNotAvailable() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(reservationRepository.existsByCarAndStartTimeBeforeAndEndTimeAfter(
                car, endTime, startTime)).thenReturn(true); // Simulate car being unavailable

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                reservationService.createReservation(reservationRequest)
        );
        assertEquals("Car is not available during the requested time.", exception.getMessage());

        verify(userRepository).findByUsername("testUser");
        verify(carRepository).findById(1L);
        verify(reservationRepository).existsByCarAndStartTimeBeforeAndEndTimeAfter(car, endTime, startTime);
        verify(reservationRepository, times(0)).save(any(Reservation.class)); // Ensure save is not called
    }

    @Test
    void testCreateReservationCarNotFound() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(carRepository.findById(1L)).thenReturn(Optional.empty()); // Simulate car not found

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                reservationService.createReservation(reservationRequest)
        );
        assertEquals("Car not found", exception.getMessage());

        verify(userRepository).findByUsername("testUser");
        verify(carRepository).findById(1L);
        verify(reservationRepository, times(0)).existsByCarAndStartTimeBeforeAndEndTimeAfter(any(), any(), any()); // Ensure this isn't called
        verify(reservationRepository, times(0)).save(any(Reservation.class)); // Ensure save is not called
    }

    @Test
    void testGetMyReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setCar(car);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);

        when(reservationRepository.findByUser_Username("testUser")).thenReturn(List.of(reservation));

        // Act
        List<Reservation> reservations = reservationService.getMyReservation();

        // Assert
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals(user, reservations.get(0).getUser());
        assertEquals(car, reservations.get(0).getCar());

        verify(reservationRepository).findByUser_Username("testUser");
    }
}
