package com.example.renal.GoRental.repository;

import com.example.renal.GoRental.model.Car;
import com.example.renal.GoRental.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByCarAndStartTimeBeforeAndEndTimeAfter(Car car, LocalDateTime endTime, LocalDateTime startTime);
    List<Reservation> findByUser_Username(String username);
}
