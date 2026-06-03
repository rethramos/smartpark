package com.rethramos.smartpark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBSeeder implements CommandLineRunner  {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void run(String... args) throws Exception {
        Address a = new Address();
        a.setCity("Calamba");
        a.setProvince("Laguna");
        a.setCountry("PH");
        addressRepository.save(a);
    }
}    
