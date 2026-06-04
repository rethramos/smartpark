package com.rethramos.smartpark.vehicle;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public @ResponseBody Vehicle create(@RequestBody CreateVehicleDto entity) {
        return vehicleService.create(vehicleService.toVehicle(entity));
    }

    @PostMapping("/{id}/actions/check_in")
    public @ResponseBody Vehicle checkIn(@PathVariable Long id, @RequestBody CheckInDto dto) {
        return vehicleService.checkIn(id, dto.parkingLotId());
    }

}
