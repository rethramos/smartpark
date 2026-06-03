package com.rethramos.smartpark.parking;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/parking_lots")
public class ParkingLotController {
    private ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping
    public @ResponseBody ParkingLot create(@RequestBody CreateParkingLotDto createParkingLotDto) {
        return this.parkingLotService.create(parkingLotService.toParkingLot(createParkingLotDto));

    }

    @GetMapping
    public @ResponseBody List<ParkingLot> getMany() {
        return parkingLotService.findAll();
    }

}
