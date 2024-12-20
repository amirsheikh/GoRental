package com.example.renal.GoRental.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.locationtech.jts.geom.Point;  // Use the JTS Point
import javax.persistence.Id;
import java.awt.*;

@Entity
@Data

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String licensePlate;
    private Point location;
}
