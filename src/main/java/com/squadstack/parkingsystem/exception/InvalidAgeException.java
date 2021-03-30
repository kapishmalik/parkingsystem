package com.squadstack.parkingsystem.exception;

public class InvalidAgeException extends AbstractParkingLotRuntimeException {

    public InvalidAgeException() {

        super(ErrorCode.INVALID_AGE);
    }
}
