package com.example.renal.GoRental.controller.reservation;

import com.example.renal.GoRental.controller.reservation.dto.ReservationFull;
import com.example.renal.GoRental.controller.reservation.dto.ReservationRequest;
import com.example.renal.GoRental.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ReservationFull createReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            return ReservationFull.of(reservationService.createReservation(reservationRequest));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public List<ReservationFull> getMyReservations() {
        return reservationService.getMyReservation().stream()
                .map(ReservationFull::of).collect(Collectors.toList());
    }
}
