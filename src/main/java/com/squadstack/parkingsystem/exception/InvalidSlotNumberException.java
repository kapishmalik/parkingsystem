package com.squadstack.parkingsystem.exception;

public class InvalidSlotNumberException extends AbstractParkingLotRuntimeException {

    public InvalidSlotNumberException() {

        super(ErrorCode.INVALID_SLOT_NUMBER);
    }
}
