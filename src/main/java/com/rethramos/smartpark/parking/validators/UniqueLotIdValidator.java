package com.rethramos.smartpark.parking.validators;

import org.springframework.stereotype.Component;

import com.rethramos.smartpark.parking.ParkingLotRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueLotIdValidator implements ConstraintValidator<UniqueLotId, String> {

    private ParkingLotRepository parkingLotRepository;

    public UniqueLotIdValidator(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !parkingLotRepository.existsByLotId(value);
    }

}
