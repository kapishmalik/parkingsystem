package com.squadstack.parkingsystem.model;

import java.util.Objects;

/**
 * Model class storing driver details - age
 */
public class Driver {

    private int age;

    public Driver(final int age) {

        this.age = age;
    }

    public int getAge() {

        return age;
    }

    public boolean isOfAge(final int age) {

        return this.age == age;
    }

    @Override public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Driver driver = (Driver) o;
        return age == driver.age;
    }

    @Override public int hashCode() {

        return Objects.hash(age);
    }
}
