package com.rethramos.smartpark.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rethramos.smartpark.parking.ParkingLot;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Integer countByParkingLot(ParkingLot parkingLot);
}
