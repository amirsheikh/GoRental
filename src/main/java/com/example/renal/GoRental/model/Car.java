package com.example.renal.GoRental.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.locationtech.jts.geom.Point;  // Use the JTS Point
import javax.persistence.Id;

@Entity
@Data
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private CarBrands brand;
    private String model;
    private String licensePlate;
    private Point location;

    public Car() {

    }

    public Car(Long id, CarBrands brand, String model, String licensePlate, Point location) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.location = location;
    }
}
