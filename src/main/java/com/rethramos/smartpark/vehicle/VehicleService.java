package com.rethramos.smartpark.vehicle;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rethramos.smartpark.foundation.PersonRepository;
import com.rethramos.smartpark.parking.ParkingLot;
import com.rethramos.smartpark.parking.ParkingLotRepository;

@Service
public class VehicleService {
    private final ParkingLotRepository parkingLotRepository;
    private VehicleRepository vehicleRepository;
    private PersonRepository personRepository;
    private VehicleTypeRepository vehicleTypeRepository;

    public VehicleService(VehicleRepository vehicleRepository, VehicleTypeRepository vehicleTypeRepository,
            PersonRepository personRepository, ParkingLotRepository parkingLotRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.personRepository = personRepository;
        this.parkingLotRepository = parkingLotRepository;
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

    public Vehicle checkOut(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();

        if (vehicle.getParkingLot() == null) {
            return vehicle;
        }

        ParkingLot origParking = parkingLotRepository.findById(vehicle.getParkingLot().getId()).orElseThrow();
        vehicle.setParkingLot(null);
        vehicleRepository.save(vehicle);
        Integer countByParkingLot = vehicleRepository.countByParkingLot(origParking);

        ParkingLot parkingLot = parkingLotRepository.findById(origParking.getId()).map(p -> {
            p.setOccupiedSpaces(countByParkingLot);
            return p;
        }).orElseThrow();
        parkingLotRepository.save(parkingLot);

        return vehicleRepository.findById(vehicle.getId()).orElseThrow();
    }

    public Vehicle toVehicle(CreateVehicleDto dto) {
        Vehicle v = new Vehicle();
        v.setLicensePlate(dto.licensePlate());
        v.setOwner(personRepository.findById(dto.ownerId()).orElseThrow());
        v.setVehicleType(vehicleTypeRepository.findByCode(dto.vehicleTypeCode()).orElseThrow());

        return v;
    }
}
