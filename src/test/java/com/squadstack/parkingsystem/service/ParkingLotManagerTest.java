package com.squadstack.parkingsystem.service;

import com.squadstack.parkingsystem.exception.OutOfCapacityException;
import com.squadstack.parkingsystem.model.Car;
import com.squadstack.parkingsystem.model.Driver;
import com.squadstack.parkingsystem.model.ParkingSlot;
import com.squadstack.parkingsystem.model.Vehicle;
import com.squadstack.parkingsystem.parkinglot.IParkingLot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ParkingLotManagerTest {

    @Mock
    private IParkingLot parkingLot;

    @InjectMocks
    private ParkingLotManager parkingLotManager;

    private final PrintStream standardOut = System.out;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {

        System.setOut(standardOut);
    }

    @Test
    void testParkVehicleCommandWhenSlotExists() {

        Vehicle vehicle = new Car("KA-01-HH-1234");
        Driver driver = new Driver(23);
        when(parkingLot.parkVehicle(eq(vehicle), eq(driver))).thenReturn(Optional.of(1));
        parkingLotManager.executeCommand("Park KA-01-HH-1234 driver_age 23");

        Assertions.assertEquals("Car with vehicle registration number \"KA-01-HH-1234\" has been parked at slot number 1",
                                outputStreamCaptor.toString().trim());
    }

    @Test
    void testParkVehicleCommandWhenParkingSpaceIsFull() {

        Vehicle vehicle = new Car("KA-01-HH-1234");
        Driver driver = new Driver(23);
        when(parkingLot.parkVehicle(eq(vehicle), eq(driver))).thenThrow(new OutOfCapacityException());
        parkingLotManager.executeCommand("Park KA-01-HH-1234 driver_age 23");

        Assertions.assertEquals("4000 : Parking space is full", outputStreamCaptor.toString().trim());
    }

    @Test
    void testVacateSlotCommand() {

        Vehicle vehicle = new Car("KA-01-HH-1234");
        Driver driver = new Driver(23);
        ParkingSlot parkingSlot = new ParkingSlot(3, vehicle, driver);
        when(parkingLot.vacateSlot(eq(3))).thenReturn(Optional.of(parkingSlot));

        parkingLotManager.executeCommand("Leave 3");

        Assertions.assertEquals("Slot number 3 vacated, the car with vehicle registration number \"KA-01-HH-1234\" left the space, the " +
                                        "driver of the car was of age 23", outputStreamCaptor.toString().trim());
    }

    @Test
    void testVacateSlotCommandWhenSlotIsAlreadyVacant() {

        when(parkingLot.vacateSlot(eq(3))).thenReturn(Optional.empty());

        parkingLotManager.executeCommand("Leave 3");
        Assertions.assertEquals("Slot already vacant", outputStreamCaptor.toString().trim());
    }

    @Test
    void testGetSlotNumbersForDriverOfAge() {

        when(parkingLot.getSlotNumbersWithDriverOfGivenAge(eq(21))).thenReturn(Arrays.asList(1, 2, 3, 4));

        parkingLotManager.executeCommand("Slot_numbers_for_driver_of_age 21");
        Assertions.assertEquals("1,2,3,4", outputStreamCaptor.toString().trim());
    }

    @Test
    void testNoSlotsForDriverOfParticularAge() {

        when(parkingLot.getSlotNumbersWithDriverOfGivenAge(eq(21))).thenReturn(new ArrayList<>());

        parkingLotManager.executeCommand("Slot_numbers_for_driver_of_age 21");
        Assertions.assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void testGetSlotNumberOfVehicleParked() {

        when(parkingLot.getSlotNumber(eq("PB-01-HH-1234"))).thenReturn(Optional.of(1));

        parkingLotManager.executeCommand("Slot_number_for_car_with_number PB-01-HH-1234");
        Assertions.assertEquals("1", outputStreamCaptor.toString().trim());
    }

    @Test
    void testGetRegistrationNumberOfVehiclesHavingDriversOfAge() {

        when(parkingLot.getVehicleRegistrationNumbersBasedOnDriverAge(eq(21)))
                .thenReturn(Arrays.asList("HR-12-AB-1234", "DL-44-CC-9876"));

        parkingLotManager.executeCommand("Vehicle_registration_number_for_driver_of_age 21");
        Assertions.assertEquals("HR-12-AB-1234,DL-44-CC-9876", outputStreamCaptor.toString().trim());
    }

    @Test
    void testNoVehiclesFoundWithDriverOfAge() {

        when(parkingLot.getVehicleRegistrationNumbersBasedOnDriverAge(eq(21))).thenReturn(new ArrayList<>());

        parkingLotManager.executeCommand("Vehicle_registration_number_for_driver_of_age 21");
        Assertions.assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void testWhenInvalidCommandIsPassed() {

        parkingLotManager.executeCommand("Hello Parking 1234");
        Assertions.assertEquals("Invalid command", outputStreamCaptor.toString().trim());
    }


}