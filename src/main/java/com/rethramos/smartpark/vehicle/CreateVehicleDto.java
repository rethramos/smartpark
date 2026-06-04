package com.rethramos.smartpark.vehicle;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record CreateVehicleDto(
        @NotEmpty @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "must contain only letters, numbers, and dashes") String licensePlate,
        @NotEmpty String vehicleTypeCode,
        @NotEmpty @Pattern(regexp = "^[A-Za-z ]+$", message = "must contain only letters and spaces") String ownerName) {

}
