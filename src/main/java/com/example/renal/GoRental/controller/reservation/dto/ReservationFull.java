package com.example.renal.GoRental.controller.reservation.dto;

import com.example.renal.GoRental.controller.auth.dto.UserFull;
import com.example.renal.GoRental.controller.car.dto.CarFull;
import com.example.renal.GoRental.model.Reservation;
import com.example.renal.GoRental.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationFull {
    CarFull car;
    UserFull user;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long id;

    public static ReservationFull of(Reservation reservation) {
        return ReservationFull.builder()
                .user(UserFull.of(reservation.getUser()))
                .car(CarFull.of(reservation.getCar()))
                .startTime(reservation.getStartTime())
                .id(reservation.getId())
                .endTime(reservation.getEndTime()).build();
    }
}
