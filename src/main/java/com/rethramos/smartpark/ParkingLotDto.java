package com.rethramos.smartpark;

public record ParkingLotDto(String lotId, Long locationId, Integer capacity, Integer occupiedSpaces) {

}
