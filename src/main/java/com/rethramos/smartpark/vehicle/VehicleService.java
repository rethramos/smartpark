package com.rethramos.smartpark.vehicle;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rethramos.smartpark.parking.ParkingLot;
import com.rethramos.smartpark.parking.ParkingLotRepository;
import com.rethramos.smartpark.vehicle.exceptions.ParkingLotFullException;

import jakarta.persistence.EntityManager;

@Service
public class VehicleService {
    private final ParkingLotRepository parkingLotRepository;
    private VehicleRepository vehicleRepository;
    private VehicleTypeRepository vehicleTypeRepository;
    private EntityManager entityManager;

    public VehicleService(VehicleRepository vehicleRepository, VehicleTypeRepository vehicleTypeRepository,
            ParkingLotRepository parkingLotRepository, EntityManager entityManager) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
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

        if (parkingLot.getOccupiedSpaces() >= parkingLot.getCapacity()) {
            throw new ParkingLotFullException();
        }

        vehicle.setParkingLot(parkingLot);
        Vehicle saved = vehicleRepository.save(vehicle);
        entityManager.flush();
        entityManager.refresh(saved);

        return saved;
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
        v.setOwnerName(dto.ownerName());
        v.setVehicleType(vehicleTypeRepository.findByCode(dto.vehicleTypeCode()).orElseThrow());

        return v;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> findByParkingLot(Long parkingLotId) {
        return parkingLotRepository.findById(parkingLotId).map(p -> vehicleRepository.findByParkingLot(p))
                .orElse(List.of());
    }

    public List<VehicleType> findAllVehicleTypes() {
        return vehicleTypeRepository.findAll();
    } 
}
