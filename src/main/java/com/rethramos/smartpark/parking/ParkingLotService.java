package com.rethramos.smartpark.parking;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rethramos.smartpark.foundation.AddressRepository;
import com.rethramos.smartpark.foundation.AddressService;

@Service
public class ParkingLotService {
    private ParkingLotRepository parkingLotRepository;
    private AddressService addressService;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, AddressRepository addressRepository,
            AddressService addressService) {
        this.parkingLotRepository = parkingLotRepository;
        this.addressService = addressService;
    }

    @Transactional
    public ParkingLot create(ParkingLot parkingLot) {
        addressService.create(parkingLot.getLocation());
        return parkingLotRepository.save(parkingLot);
    }

    public ParkingLot toParkingLot(CreateParkingLotDto dto) {
        ParkingLot p = new ParkingLot();
        p.setLotId(dto.lotId());
        p.setCapacity(dto.capacity());
        p.setOccupiedSpaces(0);
        p.setLocation(addressService.toAddress(dto.location()));

        return p;
    }

    public List<ParkingLot> findAll() {
        return parkingLotRepository.findAll();
    }

    public ParkingLot findById(Long id) {
        return parkingLotRepository.findById(id).orElseThrow();
    }
}
