package com.rethramos.smartpark.vehicle;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/vehicle_types")
public class VehicleTypeController {
    private final VehicleService vehicleService;

    VehicleTypeController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public @ResponseBody List<VehicleType> readMany() {
        return vehicleService.findAllVehicleTypes();
    }
    
}
