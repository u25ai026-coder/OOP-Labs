package com.smartparking;

// custom exception for when we can't find a vehicle
// thrown when user gives wrong plate number or wrong slot id during checkout
// also extends RuntimeException for same reason as ParkingFullException

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String message) {
        super(message);
    }
}
