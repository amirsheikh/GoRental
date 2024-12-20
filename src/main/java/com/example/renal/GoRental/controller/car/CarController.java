package com.example.renal.GoRental.controller.car;

import com.example.renal.GoRental.controller.car.dto.AvailableCarRequest;
import com.example.renal.GoRental.controller.car.dto.CarFullDTO;
import com.example.renal.GoRental.model.Car;
import com.example.renal.GoRental.service.CarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<CarFullDTO> getAvailableCars(@RequestBody AvailableCarRequest request) {
        return carService.findAvailableCars(request).stream()
                .map(CarFullDTO::of).collect(Collectors.toList());
    }
}
