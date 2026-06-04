package com.rethramos.smartpark;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rethramos.smartpark.foundation.Person;
import com.rethramos.smartpark.foundation.PersonRepository;
import com.rethramos.smartpark.vehicle.VehicleType;
import com.rethramos.smartpark.vehicle.VehicleTypeRepository;

@Component
public class DBSeeder implements CommandLineRunner {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Doe");
        personRepository.save(p);

        VehicleType car = new VehicleType();
        car.setCode("CAR");
        car.setName("Car");
        VehicleType motor = new VehicleType();
        motor.setCode("MOTOR");
        motor.setName("Motor");
        VehicleType truck = new VehicleType();
        truck.setCode("TRUCK");
        truck.setName("Truck");
        vehicleTypeRepository.saveAll(List.of(car, truck, motor));
    }
}
