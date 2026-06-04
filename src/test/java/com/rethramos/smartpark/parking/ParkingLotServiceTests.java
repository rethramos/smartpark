package com.rethramos.smartpark.parking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rethramos.smartpark.foundation.Address;
import com.rethramos.smartpark.foundation.AddressRepository;
import com.rethramos.smartpark.foundation.AddressService;

@ExtendWith(MockitoExtension.class)
public class ParkingLotServiceTests {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private ParkingLotService parkingLotService;

    @Test
    void testCreateWhenCalledThenCreateAddressAndSaveParkingLot() {
        Address addr = new Address();
        addr.setId(5L);

        ParkingLot expected = new ParkingLot();
        expected.setId(1L);
        expected.setLocation(addr);

        given(addressService.create(expected.getLocation())).willReturn(addr);
        given(parkingLotRepository.save(expected)).willReturn(expected);

        ParkingLot actual = parkingLotService.create(expected);

        then(addressService).should().create(addr);
        then(parkingLotRepository).should().save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void testFindAllWhenCalledThenReturnAllParkingLots() {
        List<ParkingLot> expected = List.of(new ParkingLot(), new ParkingLot());
        given(parkingLotRepository.findAll()).willReturn(expected);

        List<ParkingLot> actual = parkingLotService.findAll();

        then(parkingLotRepository).should().findAll();
        assertEquals(expected, actual);
    }

    @Test
    void testFindByIdWhenFoundThenReturnParkingLot() {
        ParkingLot expected = new ParkingLot();
        expected.setId(1L);

        given(parkingLotRepository.findById(1L)).willReturn(Optional.of(expected));

        ParkingLot actual = parkingLotService.findById(1L);

        then(parkingLotRepository).should().findById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void testFindByIdWhenNotFoundThenThrow() {
        given(parkingLotRepository.findById(99L)).willReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> parkingLotService.findById(99L));
    }
}
