package com.example.renal.GoRental.service;

import com.example.renal.GoRental.controller.car.dto.AvailableCarRequest;
import com.example.renal.GoRental.model.Car;
import com.example.renal.GoRental.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAvailableCars(AvailableCarRequest request) {
        return carRepository.findAvailableCars(
                request.getLongitude(),
                request.getLongitude(),
                request.getRadius(),
                request.getStartTime(),
                request.getEndTime());
    }
}