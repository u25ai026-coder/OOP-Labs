package com.smartparking;

// custom exception for when parking lot is completely full
// I extend RuntimeException so I don't need to write "throws" everywhere
// our professor explained the difference between checked and unchecked exceptions
// RuntimeException is unchecked - easier to use for this kind of error

public class ParkingFullException extends RuntimeException {
    public ParkingFullException(String message) {
        super(message);
    }
}
