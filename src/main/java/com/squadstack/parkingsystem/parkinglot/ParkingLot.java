package com.squadstack.parkingsystem.parkinglot;

import com.squadstack.parkingsystem.model.Driver;
import com.squadstack.parkingsystem.model.ParkingSlot;
import com.squadstack.parkingsystem.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface ParkingLot {


    /**
     * @param vehicle vehicle details - registration number
     * @param driver  driver details - age
     * @return slot number where vehicle is parked or throw OutOfCapacityException
     */
    Optional<Integer> parkVehicle(final Vehicle vehicle,
                                  final Driver driver);

    /**
     * @param slotNumber slot number which needs to be vacated
     * @return parking slot details which got vacated
     */
    Optional<ParkingSlot> vacateSlot(final int slotNumber);

    /**
     * @param age driver age
     * @return list of vehicle registration number based on driver age
     */
    List<String> getVehicleRegistrationNumbersBasedOnDriverAge(final int age);

    /**
     * @param registrationNumber - vehicle registration number
     * @return slot number where vehicle is parked
     */
    Optional<Integer> getSlotNumber(String registrationNumber);

    /**
     * @param age - driver age
     * @return slot numbers of vehicles which driven by driver of particular age
     */
    List<Integer> getSlotNumbersWithDriverOfGivenAge(final int age);
}
