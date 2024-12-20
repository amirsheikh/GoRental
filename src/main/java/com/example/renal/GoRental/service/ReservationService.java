package com.example.renal.GoRental.service;

import com.example.renal.GoRental.controller.reservation.dto.ReservationRequest;
import com.example.renal.GoRental.model.Car;
import com.example.renal.GoRental.model.Reservation;
import com.example.renal.GoRental.model.User;
import com.example.renal.GoRental.repository.CarRepository;
import com.example.renal.GoRental.repository.ReservationRepository;
import com.example.renal.GoRental.repository.UserRepository;
import com.example.renal.GoRental.security.UserContextUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ReservationService {

    private CarRepository carRepository;

    private ReservationRepository reservationRepository;

    private UserRepository userRepository;

    public ReservationService(CarRepository carRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    public Reservation createReservation(ReservationRequest request) {

        User user = userRepository.findByUsername(UserContextUtil.getCurrentUser());
        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        boolean isCarAvailable = !reservationRepository.existsByCarAndStartTimeBeforeAndEndTimeAfter(
                car, request.getEndTime(), request.getStartTime());
        if (!isCarAvailable) {
            throw new IllegalStateException("Car is not available during the requested time.");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setCar(car);
        reservation.setStartTime(request.getStartTime());
        reservation.setEndTime(request.getEndTime());

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getMyReservation() {
        return reservationRepository.findByUser_Username(UserContextUtil.getCurrentUser());
    }
}
