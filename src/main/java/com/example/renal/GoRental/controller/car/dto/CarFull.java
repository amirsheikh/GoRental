package com.example.renal.GoRental.controller.car.dto;

import com.example.renal.GoRental.model.Car;
import com.example.renal.GoRental.model.CarBrands;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarFull {

    private CarBrands brand;
    private String model;
    private Address location;
    private long id;

    public static CarFull of(Car car) {
        return CarFull.builder()
                .location(Address.builder()
                        .lat(car.getLocation().getY())
                        .lon(car.getLocation().getX()).build())
                .brand(car.getBrand())
                .id(car.getId())
                .model(car.getModel()).build();
    }

    @Data
    @Builder
    private static class Address {
        private double lat;
        private double lon;
    }

}
