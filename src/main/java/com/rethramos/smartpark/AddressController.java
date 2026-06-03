package com.rethramos.smartpark;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/addresses")
public class AddressController {
    private AddressRepository addressRepository;

    
    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    @PostMapping
    public @ResponseBody Address create(@RequestBody Address address) {
        
        return addressRepository.save(address);
    }
    
}
