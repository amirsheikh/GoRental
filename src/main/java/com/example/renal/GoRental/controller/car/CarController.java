package com.example.renal.GoRental.controller.car;

import com.example.renal.GoRental.controller.car.dto.AvailableCarRequest;
import com.example.renal.GoRental.controller.car.dto.CarFull;
import com.example.renal.GoRental.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/available")
    public List<CarFull> getAvailableCars(@RequestBody AvailableCarRequest request) {
        return carService.findAvailableCars(request).stream()
                .map(CarFull::of).collect(Collectors.toList());
    }

    @GetMapping("/{carId}")
    public CarFull getCarDetails(@PathVariable Long carId) {
        return carService.getCar(carId)
                .map(CarFull::of)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
