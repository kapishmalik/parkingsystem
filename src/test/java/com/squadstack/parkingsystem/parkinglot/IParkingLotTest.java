package com.squadstack.parkingsystem.parkinglot;


import com.squadstack.parkingsystem.exception.OutOfCapacityException;
import com.squadstack.parkingsystem.model.Car;
import com.squadstack.parkingsystem.model.Driver;
import com.squadstack.parkingsystem.model.ParkingSlot;
import com.squadstack.parkingsystem.model.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

class IParkingLotTest {


    @Test
    void testVehicleIsParkedOnFirstSlot() {

        ParkingLot parkingLot = new IParkingLot(2);
        Optional<Integer> slotNumber = parkingLot.parkVehicle(new Car("DL-41-CG-1234"), new Driver(21));
        Assertions.assertTrue(slotNumber.isPresent());
        Assertions.assertEquals(1, slotNumber.get());
    }

    @Test
    void testVehicleIsParkedOnSecondSlot() {

        ParkingLot parkingLot = new IParkingLot(2);
        parkingLot.parkVehicle(new Car("DL-41-CG-1234"), new Driver(21));
        Optional<Integer> secondSlotNumber = parkingLot.parkVehicle(new Car("DL-31-CG-1234"), new Driver(21));
        Assertions.assertTrue(secondSlotNumber.isPresent());
        Assertions.assertEquals(2, secondSlotNumber.get());
    }

    @Test
    void testParkingSpaceIsFull() {

        ParkingLot parkingLot = new IParkingLot(2);
        parkingLot.parkVehicle(new Car("DL-41-CG-1234"), new Driver(21));
        parkingLot.parkVehicle(new Car("HR-41-CG-1234"), new Driver(31));
        Assertions.assertThrows(OutOfCapacityException.class,
                                () -> parkingLot.parkVehicle(new Car("HR-41-CG-1234"), new Driver(31)));
    }

    @Test
    void testVacateSlot() {

        ParkingLot parkingLot = new IParkingLot(2);
        Vehicle vehicle = new Car("DL-41-CG-1234");
        Driver driver = new Driver(21);
        parkingLot.parkVehicle(vehicle, driver);
        Optional<ParkingSlot> optionalParkingSlot = parkingLot.vacateSlot(1);
        Assertions.assertTrue(optionalParkingSlot.isPresent());
        Assertions.assertEquals(new ParkingSlot(1, vehicle, driver), optionalParkingSlot.get());
    }

    @Test
    void testVacateSlotWhichIsAlreadyVacant() {

        ParkingLot parkingLot = new IParkingLot(2);
        Optional<ParkingSlot> optionalParkingSlot = parkingLot.vacateSlot(1);
        Assertions.assertFalse(optionalParkingSlot.isPresent());
    }

    @Test
    void testGetListOfRegistrationNumbersBasedOnAge() {

        ParkingLot parkingLot = new IParkingLot(2);
        parkingLot.parkVehicle(new Car("DL-41-CG-1234"), new Driver(21));
        parkingLot.parkVehicle(new Car("HR-41-CG-1234"), new Driver(21));
        Assertions.assertEquals(Arrays.asList("DL-41-CG-1234", "HR-41-CG-1234"),
                                parkingLot.getVehicleRegistrationNumbersBasedOnDriverAge(21));
    }

    @Test
    void testGetEmptyListOfRegistrationNumberBasedOnAge() {

        ParkingLot parkingLot = new IParkingLot(2);
        parkingLot.parkVehicle(new Car("DL-41-CG-1234"), new Driver(21));
        parkingLot.parkVehicle(new Car("HR-41-CG-1234"), new Driver(21));
        Assertions.assertEquals(new ArrayList<>(),
                                parkingLot.getVehicleRegistrationNumbersBasedOnDriverAge(41));

    }

    @Test
    void testGetSlotNumbersOfVehiclesWithDriverOfParticularAge() {

        ParkingLot parkingLot = new IParkingLot(2);
        parkingLot.parkVehicle(new Car("DL-41-CG-1234"), new Driver(31));
        parkingLot.parkVehicle(new Car("HR-41-CG-1234"), new Driver(21));
        Assertions.assertEquals(Arrays.asList(2),
                                parkingLot.getSlotNumbersWithDriverOfGivenAge(21));
    }

    @Test
    void testEmptyListOfSlotNumberIsReturnedIfNoVehicleHasDriverWithGivenAge() {

        ParkingLot parkingLot = new IParkingLot(2);
        parkingLot.parkVehicle(new Car("DL-41-CG-1234"), new Driver(31));
        Assertions.assertEquals(new ArrayList<>(),
                                parkingLot.getSlotNumbersWithDriverOfGivenAge(41));
    }

    @Test
    void testGetSlotNumberOfParticularVehicle() {

        ParkingLot parkingLot = new IParkingLot(2);
        parkingLot.parkVehicle(new Car("DL-41-CG-1234"), new Driver(31));
        parkingLot.vacateSlot(1);
        parkingLot.parkVehicle(new Car("HR-41-CG-1234"), new Driver(21));
        Optional<Integer> optionalSlotNumber = parkingLot.getSlotNumber("HR-41-CG-1234");
        Assertions.assertTrue(optionalSlotNumber.isPresent());
        Assertions.assertEquals(1, optionalSlotNumber.get());
    }
}