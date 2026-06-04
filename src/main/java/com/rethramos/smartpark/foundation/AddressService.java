package com.rethramos.smartpark.foundation;

import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address create(Address address) {
        return addressRepository.save(address);
    }

    public Address toAddress(CreateAddressDto dto) {
        Address a = new Address();
        a.setLine1(dto.line1());
        a.setLine2(dto.line2());
        a.setCity(dto.city());
        a.setProvince(dto.province());
        a.setCountry(dto.country());

        return a;
    }
}
