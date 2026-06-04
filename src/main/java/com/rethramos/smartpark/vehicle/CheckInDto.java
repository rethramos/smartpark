package com.rethramos.smartpark.vehicle;

import jakarta.validation.constraints.NotEmpty;

public record CheckInDto(@NotEmpty Long parkingLotId) {

}
