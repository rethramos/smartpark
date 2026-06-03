package com.rethramos.smartpark.vehicle;

import com.rethramos.smartpark.parking.ParkingLot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String licensePlate;
    @OneToOne(optional = false)
    private VehicleType vehicleType;
    // Person owner;
    @ManyToOne
    private ParkingLot parking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public ParkingLot getParking() {
        return parking;
    }

    public void setParking(ParkingLot parking) {
        this.parking = parking;
    }

}
