package com.rethramos.smartpark.vehicle.exeptions;

public class ParkingLotFullException extends RuntimeException {

    @Override
    public String getMessage() {
        return "parking lot full";
    }

}
