package com.example.renal.GoRental.controller.car.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AvailableCarRequest {
    private double latitude;
    private double longitude;
    private double radius;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
