package com.rethramos.smartpark.parking;

import com.rethramos.smartpark.foundation.CreateAddressDto;

public record CreateParkingLotDto(String lotId, CreateAddressDto location, Integer capacity) {
    // TODO: add validations (required)

}
