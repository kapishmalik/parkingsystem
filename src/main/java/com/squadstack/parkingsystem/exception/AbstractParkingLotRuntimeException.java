package com.squadstack.parkingsystem.exception;

public class AbstractParkingLotRuntimeException extends RuntimeException {

    private ErrorCode errorCode;

    AbstractParkingLotRuntimeException(final ErrorCode errorCode) {

        super(errorCode.getDefaultErrorMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {

        return errorCode;
    }

}
