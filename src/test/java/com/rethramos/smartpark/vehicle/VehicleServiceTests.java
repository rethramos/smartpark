package com.rethramos.smartpark.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rethramos.smartpark.parking.ParkingLot;
import com.rethramos.smartpark.parking.ParkingLotRepository;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTests {
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleTypeRepository vehicleTypeRepository;
    @Mock
    private ParkingLotRepository parkingLotRepository;
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private VehicleService vehicleService;

    @Captor
    private ArgumentCaptor<ParkingLot> parkingLotCaptor;

    @Test
    void testCreateWhenValidThenReturnVehicle() {
        Vehicle v = mock(Vehicle.class);
        given(vehicleRepository.save(any(Vehicle.class))).willReturn(v);

        Vehicle created = vehicleService.create(v);
        assertNotNull(created);
    }

    @Test
    void testCheckInWhenAlreadyInSameParkingThenDoNothing() {
        ParkingLot p = new ParkingLot();
        p.setId(10L);
        p.setCapacity(1);
        p.setOccupiedSpaces(0);

        Vehicle expected = new Vehicle();
        expected.setId(1L);
        expected.setParkingLot(p);

        given(parkingLotRepository.findById(10L)).willReturn(Optional.of(p));
        given(vehicleRepository.findById(1L)).willReturn(Optional.of(expected));

        Vehicle actual = vehicleService.checkIn(1L, 10L);

        then(vehicleRepository).should(never()).save(any(Vehicle.class));
        then(parkingLotRepository).should(never()).save(any(ParkingLot.class));
        assertEquals(expected, actual);
    }

    @Test
    void testCheckInWhenParkingFullThenThrowConstraintViolationException() {
        ParkingLot p = new ParkingLot();
        p.setId(10L);
        p.setCapacity(2);
        p.setOccupiedSpaces(2);

        Vehicle v = new Vehicle();
        v.setId(1L);
        v.setParkingLot(null);

        given(parkingLotRepository.findById(10L)).willReturn(Optional.of(p));
        given(vehicleRepository.findById(1L)).willReturn(Optional.of(v));

        assertThrows(ConstraintViolationException.class, () -> vehicleService.checkIn(1L, 10L));

        then(vehicleRepository).should(never()).save(any());
        then(parkingLotRepository).should(never()).save(any(ParkingLot.class));
    }

    @Test
    void testCheckInWhenSpaceAvailableThenAssignParkingAndUpdateOccupiedSpaces() {
        ParkingLot targetParking = new ParkingLot();
        targetParking.setId(10L);
        targetParking.setCapacity(2);
        targetParking.setOccupiedSpaces(1);

        Vehicle vehicleToCheckIn = new Vehicle();
        vehicleToCheckIn.setId(1L);
        vehicleToCheckIn.setParkingLot(null);

        Vehicle saved = new Vehicle();
        saved.setId(1L);
        saved.setParkingLot(targetParking);

        given(parkingLotRepository.findById(10L)).willReturn(Optional.of(targetParking));
        given(vehicleRepository.findById(1L)).willReturn(Optional.of(vehicleToCheckIn));
        given(vehicleRepository.findById(1L)).willReturn(Optional.of(saved));

        Vehicle actual = vehicleService.checkIn(1L, 10L);

        assertEquals(10L, actual.getParkingLot().getId());
    }

    @Test
    void testCheckOutWhenNotInParkingThenDoNothing() {
        Vehicle expected = new Vehicle();
        expected.setId(1L);
        expected.setParkingLot(null);

        given(vehicleRepository.findById(1L)).willReturn(Optional.of(expected));

        Vehicle actual = vehicleService.checkOut(1L);

        then(vehicleRepository).should(never()).save(any());
        assertEquals(expected, actual);
    }

    @Test
    void testCheckOutWhenInParkingThenRemoveParkingAndUpdateOccupiedSpaces() {
        ParkingLot p = new ParkingLot();
        p.setId(10L);
        p.setCapacity(5);
        p.setOccupiedSpaces(3);

        Vehicle v = new Vehicle();
        v.setId(1L);
        v.setParkingLot(p);

        ParkingLot orig = new ParkingLot();
        orig.setId(10L);
        orig.setOccupiedSpaces(3);

        given(vehicleRepository.findById(1L)).willReturn(Optional.of(v));
        given(parkingLotRepository.findById(10L)).willReturn(Optional.of(orig));
        given(vehicleRepository.save(any(Vehicle.class))).willAnswer(inv -> inv.getArgument(0));
        given(vehicleRepository.countByParkingLot(orig)).willReturn(2);
        given(parkingLotRepository.findById(10L)).willReturn(Optional.of(orig));
        given(parkingLotRepository.save(any(ParkingLot.class))).willAnswer(inv -> inv.getArgument(0));
        given(vehicleRepository.findById(1L)).willReturn(Optional.of(v));

        Vehicle result = vehicleService.checkOut(1L);

        then(vehicleRepository).should().save(any(Vehicle.class));
        then(parkingLotRepository).should().save(parkingLotCaptor.capture());

        ParkingLot updated = parkingLotCaptor.getValue();
        assertEquals(2, updated.getOccupiedSpaces());
        assertNull(result.getParkingLot());
    }

    @Test
    void testFindByParkingLotWhenNullThenReturnEmptyList() {
        given(parkingLotRepository.findById(null)).willReturn(Optional.empty());

        List<Vehicle> items = vehicleService.findByParkingLot(null);
        assertTrue(items.isEmpty());
    }

    @Test
    void testFindByParkingLotWhenNotFoundThenReturnEmptyList() {
        given(parkingLotRepository.findById(99L)).willReturn(Optional.empty());

        List<Vehicle> result = vehicleService.findByParkingLot(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByParkingLotWhenFoundThenReturnList() {
        ParkingLot p = mock(ParkingLot.class);
        given(parkingLotRepository.findById(anyLong())).willReturn(Optional.of(p));
        Vehicle v = mock(Vehicle.class);
        List<Vehicle> expected = List.of(v);
        given(vehicleRepository.findByParkingLot(any(ParkingLot.class))).willReturn(expected);

        List<Vehicle> actual = vehicleService.findByParkingLot(2L);
        assertFalse(actual.isEmpty());
        assertTrue(actual.size() == 1);
        assertEquals(expected, actual);
    }
}
