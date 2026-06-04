package com.rethramos.smartpark.parking;

import com.rethramos.smartpark.foundation.CreateAddressDto;
import com.rethramos.smartpark.parking.validators.UniqueLotId;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateParkingLotDto(@Size(min = 1, max = 50) @NotEmpty @UniqueLotId String lotId, @Valid CreateAddressDto location,
        @NotNull @Min(value = 1) Integer capacity) {

}
