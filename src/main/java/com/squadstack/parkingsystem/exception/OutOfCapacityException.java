package com.squadstack.parkingsystem.exception;

public class OutOfCapacityException extends AbstractParkingLotRuntimeException {

    public OutOfCapacityException() {

        super(ErrorCode.PARKING_IS_FULL);
    }
}
