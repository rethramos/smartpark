package com.rethramos.smartpark.parking;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@RequestMapping(path = "/parking_lots")
public class ParkingLotController {
    private ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping
    public @ResponseBody ParkingLot create(@Valid @RequestBody CreateParkingLotDto createParkingLotDto) {
        return this.parkingLotService.create(parkingLotService.toParkingLot(createParkingLotDto));

    }

    @GetMapping
    public @ResponseBody List<ParkingLot> readMany() {
        return parkingLotService.findAll();
    }

    @GetMapping("/{id}")
    public ParkingLot readOne(@PathVariable Long id) {
        return parkingLotService.findById(id);
    }
    

}
