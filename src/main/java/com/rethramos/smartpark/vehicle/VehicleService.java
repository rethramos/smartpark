package com.rethramos.smartpark.vehicle;

import org.springframework.stereotype.Service;

import com.rethramos.smartpark.foundation.PersonRepository;

@Service
public class VehicleService {
    private VehicleRepository vehicleRepository;
    private PersonRepository personRepository;
    private VehicleTypeRepository vehicleTypeRepository;

    public VehicleService(VehicleRepository vehicleRepository, VehicleTypeRepository vehicleTypeRepository,
            PersonRepository personRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.personRepository = personRepository;
    }

    public Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle toVehicle(CreateVehicleDto dto) {
        Vehicle v = new Vehicle();
        v.setLicensePlate(dto.licensePlate());
        v.setOwner(personRepository.findById(dto.ownerId()).orElseThrow());
        v.setVehicleType(vehicleTypeRepository.findByCode(dto.vehicleTypeCode()).orElseThrow());

        return v;
    }
}
