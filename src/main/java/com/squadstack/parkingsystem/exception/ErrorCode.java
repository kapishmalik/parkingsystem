package com.squadstack.parkingsystem.exception;

public enum ErrorCode {


    PARKING_IS_FULL(4000, "Parking space is full"),
    INVALID_SLOT_NUMBER(4001, "Invalid slot number"),
    INVALID_AGE(4002, "Invalid age passed");


    private final int code;

    private final String defaultErrorMessage;

    ErrorCode(final int code,
              final String defaultErrorMessage) {

        this.code = code;
        this.defaultErrorMessage = defaultErrorMessage;
    }

    public int getCode() {

        return code;
    }

    public String getDefaultErrorMessage() {

        return defaultErrorMessage;
    }
}
