package com.rethramos.smartpark.vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rethramos.smartpark.parking.ParkingLot;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Integer countByParkingLot(ParkingLot parkingLot);

    List<Vehicle> findByParkingLot(ParkingLot parkingLot);

    boolean existsByLicensePlate(String licensePlate);
}
