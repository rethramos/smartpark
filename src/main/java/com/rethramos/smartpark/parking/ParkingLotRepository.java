package com.rethramos.smartpark.parking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    boolean existsByLotId(String lotId);
}
