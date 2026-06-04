package com.rethramos.smartpark.vehicle;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    Optional<VehicleType> findByCode(String code);
}
