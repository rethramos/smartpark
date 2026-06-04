package com.rethramos.smartpark.vehicle.validators;

import org.springframework.stereotype.Component;

import com.rethramos.smartpark.vehicle.VehicleRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueLicensePlateValidator implements ConstraintValidator<UniqueLicensePlate, String> {

    private VehicleRepository vehicleRepository;

    public UniqueLicensePlateValidator(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !vehicleRepository.existsByLicensePlate(value);
    }

}
