package com.smartparking;

import java.time.LocalDateTime;

// ParkingFeeStrategy is an interface
// interface is like a contract - any class that implements it
// must provide its own version of calculateFee method
// this is the Strategy Pattern - we can change fee logic later
// without touching the rest of the code
// for example if we want to add a weekend rate later, we just make a new class

public interface ParkingFeeStrategy {
    double calculateFee(Vehicle vehicle, LocalDateTime exitTime);
}
