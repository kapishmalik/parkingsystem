package com.squadstack.parkingsystem.model;

import java.util.Objects;

/**
 * ParkingSlot model containing slot number, vehicle and driver details
 */
public class ParkingSlot {

    private int slotNumber;

    private Vehicle vehicle;

    private Driver driver;

    public ParkingSlot(final int slotNumber,
                       final Vehicle vehicle,
                       final Driver driver) {

        this.slotNumber = slotNumber;
        this.vehicle = vehicle;
        this.driver = driver;
    }

    @Override public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParkingSlot that = (ParkingSlot) o;
        return slotNumber == that.slotNumber &&
                vehicle.equals(that.vehicle) &&
                driver.equals(that.driver);
    }

    @Override public int hashCode() {

        return Objects.hash(slotNumber, vehicle, driver);
    }

    public int getSlotNumber() {

        return slotNumber;
    }

    public Vehicle getVehicle() {

        return vehicle;
    }

    public Driver getDriver() {

        return driver;
    }
}
