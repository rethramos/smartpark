package com.rethramos.smartpark.vehicle.exceptions;

public class ParkingLotFullException extends RuntimeException {

    @Override
    public String getMessage() {
        return "parking lot full";
    }

}
