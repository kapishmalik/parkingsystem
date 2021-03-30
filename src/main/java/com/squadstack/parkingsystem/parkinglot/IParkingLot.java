package com.squadstack.parkingsystem.parkinglot;

import com.squadstack.parkingsystem.exception.InvalidAgeException;
import com.squadstack.parkingsystem.exception.InvalidSlotNumberException;
import com.squadstack.parkingsystem.exception.OutOfCapacityException;
import com.squadstack.parkingsystem.model.Driver;
import com.squadstack.parkingsystem.model.ParkingSlot;
import com.squadstack.parkingsystem.model.Vehicle;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of ParkingLot interface
 * Storing array of ParkingSlot and if slot is empty, then value is null otherwise parking slot details
 * Also, storing available slots count and total capacity
 * assumption - parking slots cannot be very large number so not storing details in  another data structure in order to optimize search
 * queries
 */
public class IParkingLot implements ParkingLot {

    private ParkingSlot[] parkingSlots;

    private int availableSlots;

    private int totalCapacity;

    public IParkingLot(final int totalCapacity) {

        this.totalCapacity = totalCapacity;
        availableSlots = totalCapacity;
        parkingSlots = new ParkingSlot[totalCapacity];
    }

    @Override
    public Optional<Integer> parkVehicle(final Vehicle vehicle,
                                         final Driver driver) {

        if (availableSlots == 0) {
            throw new OutOfCapacityException();
        }
        else {
            int vacantSlotIndex = getVacantParkingSlot();
            parkingSlots[vacantSlotIndex] = new ParkingSlot(vacantSlotIndex + 1, vehicle, driver);
            availableSlots--;
            return Optional.of(vacantSlotIndex + 1);
        }
    }

    @Override
    public Optional<ParkingSlot> vacateSlot(final int slotNumber) {

        validateSlotNumber(slotNumber);
        ParkingSlot parkingSlot = parkingSlots[slotNumber - 1];
        parkingSlots[slotNumber - 1] = null;
        availableSlots++;
        return Optional.ofNullable(parkingSlot);
    }


    @Override
    public List<String> getVehicleRegistrationNumbersBasedOnDriverAge(final int age) {

        validateDriverAge(age);
        return Arrays.stream(parkingSlots)
                     .filter(Objects::nonNull)
                     .filter(parkingSlot -> parkingSlot.getDriver().isOfAge(age))
                     .map(parkingSlot -> parkingSlot.getVehicle().getRegistrationNumber()).collect(Collectors.toList());
    }


    @Override
    public Optional<Integer> getSlotNumber(final String registrationNumber) {

        return Arrays.stream(parkingSlots)
                     .filter(Objects::nonNull)
                     .filter(parkingSlot -> parkingSlot.getVehicle().hasVehicleRegistrationNumber(registrationNumber))
                     .map(ParkingSlot::getSlotNumber)
                     .findFirst();
    }

    @Override
    public List<Integer> getSlotNumbersWithDriverOfGivenAge(final int age) {

        validateDriverAge(age);
        return Arrays.stream(parkingSlots)
                     .filter(Objects::nonNull)
                     .filter(parkingSlot -> parkingSlot.getDriver().isOfAge(age))
                     .map(ParkingSlot::getSlotNumber)
                     .collect(Collectors.toList());
    }

    /**
     * helper method to get vacant parking slot
     *
     * @return vacant parking slot starting indexed at 0
     */
    private int getVacantParkingSlot() {

        int vacantSlotIndex = -1;
        for (int i = 0; i < totalCapacity; i++) {

            if (parkingSlots[i] == null) {
                vacantSlotIndex = i;
                break;
            }
        }
        return vacantSlotIndex;
    }

    private void validateDriverAge(final int age) {

        if (age <= 0) {
            throw new InvalidAgeException();
        }
    }

    private void validateSlotNumber(final int slotNumber) {

        if (slotNumber < 1 || slotNumber > totalCapacity) {

            throw new InvalidSlotNumberException();
        }
    }
}
