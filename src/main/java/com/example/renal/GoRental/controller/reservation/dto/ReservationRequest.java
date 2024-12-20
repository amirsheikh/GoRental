package com.example.renal.GoRental.controller.reservation.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequest {
    private Long carId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
