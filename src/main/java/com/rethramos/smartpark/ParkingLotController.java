package com.rethramos.smartpark;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/parking_lots")
public class ParkingLotController {
    private ParkingLotRepository parkingLotRepository;

    public ParkingLotController(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @GetMapping
    public @ResponseBody List<ParkingLot> getMany() {
        return parkingLotRepository.findAll();
    }

}
