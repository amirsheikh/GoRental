package com.example.renal.GoRental.repository;

import com.example.renal.GoRental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT * " +
            "FROM car c " +
            "WHERE ST_DWithin(c.location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :radius) " +
            "AND c.id NOT IN (" +
            "    SELECT r.car_id " +
            "    FROM reservation r " +
            "    WHERE r.start_time < :endTime AND r.end_time > :startTime" +
            ")", nativeQuery = true)
    List<Car> findAvailableCars (
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
