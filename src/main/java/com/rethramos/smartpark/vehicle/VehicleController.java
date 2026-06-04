package com.rethramos.smartpark.vehicle;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public @ResponseBody Vehicle create(@RequestBody @Valid CreateVehicleDto dto) {
        return vehicleService.create(vehicleService.toVehicle(dto));
    }

    @PostMapping("/{id}/actions/check_in")
    public @ResponseBody Vehicle checkIn(@PathVariable Long id, @RequestBody CheckInDto dto) {
        return vehicleService.checkIn(id, dto.parkingLotId());
    }

    @PostMapping("/{id}/actions/check_out")
    public @ResponseBody Vehicle checkOut(@PathVariable Long id) {
        return vehicleService.checkOut(id);
    }

}
