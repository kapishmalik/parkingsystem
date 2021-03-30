package com.squadstack.parkingsystem.model;

import java.util.Objects;

public abstract class Vehicle {

    private String registrationNumber;

    Vehicle(final String registrationNumber) {

        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {

        return registrationNumber;
    }

    public boolean hasVehicleRegistrationNumber(final String registrationNumber) {

        return registrationNumber != null && registrationNumber.equals(this.registrationNumber);
    }

    @Override public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Vehicle vehicle = (Vehicle) o;
        return registrationNumber.equals(vehicle.registrationNumber);
    }

    @Override public int hashCode() {

        return Objects.hash(registrationNumber);
    }
}
