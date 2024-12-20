package com.example.renal.GoRental.service;

import com.example.renal.GoRental.controller.car.dto.AvailableCarRequest;
import com.example.renal.GoRental.model.Car;
import com.example.renal.GoRental.model.CarBrands;
import com.example.renal.GoRental.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private AvailableCarRequest request;
    private Car car;

    @BeforeEach
    void setUp() {
        request = AvailableCarRequest.builder()
                .longitude(51.3890)
                .latitude(35.6895)
                .radius(10)
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(2))
                .build();

        car = Car.builder()
                .id(1L)
                .brand(CarBrands.BMW)
                .model("X3")
                .licensePlate("1234-XYZ")
                .build();
    }

    @Test
    void testFindAvailableCars() {
        // Arrange
        when(carRepository.findAvailableCars(
                eq(51.389d),
                eq(51.389d), // Match longitude and latitude correctly
                eq(10.0d),
                eq(request.getStartTime()),
                eq(request.getEndTime())))
                .thenReturn(Arrays.asList(car));

        // Act
        List<Car> availableCars = carService.findAvailableCars(request);

        // Assert
        assertNotNull(availableCars);
        assertEquals(1, availableCars.size());
        assertEquals(car, availableCars.get(0));
        verify(carRepository, times(1)).findAvailableCars(
                anyDouble(),
                anyDouble(),
                eq(10.0d),
                any(),
                any());
    }

    @Test
    void testGetCar() {
        // Arrange
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        // Act
        Optional<Car> foundCar = carService.getCar(1L);

        // Assert
        assertTrue(foundCar.isPresent());
        assertEquals(car, foundCar.get());
        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCarNotFound() {
        // Arrange
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Car> foundCar = carService.getCar(1L);

        // Assert
        assertFalse(foundCar.isPresent());
        verify(carRepository, times(1)).findById(1L);
    }
}
