package com.rethramos.smartpark.vehicle;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rethramos.smartpark.foundation.PersonRepository;
import com.rethramos.smartpark.parking.ParkingLot;
import com.rethramos.smartpark.parking.ParkingLotRepository;

import jakarta.persistence.EntityManager;

@Service
public class VehicleService {
    private final ParkingLotRepository parkingLotRepository;
    private VehicleRepository vehicleRepository;
    private PersonRepository personRepository;
    private VehicleTypeRepository vehicleTypeRepository;
    private EntityManager entityManager;

    public VehicleService(VehicleRepository vehicleRepository, VehicleTypeRepository vehicleTypeRepository,
            PersonRepository personRepository, ParkingLotRepository parkingLotRepository, EntityManager entityManager) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.personRepository = personRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.entityManager = entityManager;
    }

    public Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public Vehicle checkIn(Long vehicleId, Long parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow();
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        Long currentParkingId = Objects.requireNonNullElse(vehicle.getParkingLot(), new ParkingLot()).getId();

        if (currentParkingId != null && currentParkingId.equals(parkingLotId)) {
            return vehicle;
        }

        vehicle.setParkingLot(parkingLot);
        Vehicle saved = vehicleRepository.save(vehicle);
        Integer countByParkingLot = vehicleRepository.countByParkingLot(parkingLot);
        parkingLot.setOccupiedSpaces(countByParkingLot);
        parkingLotRepository.save(parkingLot);

        return vehicleRepository.findById(saved.getId()).orElseThrow();
    }

    public Vehicle toVehicle(CreateVehicleDto dto) {
        Vehicle v = new Vehicle();
        v.setLicensePlate(dto.licensePlate());
        v.setOwner(personRepository.findById(dto.ownerId()).orElseThrow());
        v.setVehicleType(vehicleTypeRepository.findByCode(dto.vehicleTypeCode()).orElseThrow());

        return v;
    }
}
