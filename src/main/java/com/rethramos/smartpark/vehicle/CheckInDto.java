package com.rethramos.smartpark.vehicle;

import jakarta.validation.constraints.NotNull;

public record CheckInDto(@NotNull Long parkingLotId) {

}
