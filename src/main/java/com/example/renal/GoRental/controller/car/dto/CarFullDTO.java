package com.example.renal.GoRental.controller.car.dto;

import com.example.renal.GoRental.model.Car;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarFullDTO {

    private String brand;
    private String model;
    private Address location;

    public static CarFullDTO of(Car car) {
        return CarFullDTO.builder()
                .location(Address.builder()
                        .lat(car.getLocation().getY())
                        .lon(car.getLocation().getX()).build())
                .brand(car.getBrand())
                .model(car.getModel()).build();
    }

    @Data
    @Builder
    private static class Address {
        private double lat;
        private double lon;
    }

}
