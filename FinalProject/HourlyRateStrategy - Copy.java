package com.smartparking;

import java.time.Duration;
import java.time.LocalDateTime;

// HourlyRateStrategy implements ParkingFeeStrategy interface
// this class calculates fee based on how long the vehicle was parked
// minimum charge is always 1 hour even if parked for less time
// after that we calculate the exact hours and multiply by rate

public class HourlyRateStrategy implements ParkingFeeStrategy {

    @Override
    public double calculateFee(Vehicle vehicle, LocalDateTime exitTime) {

        // Duration.between calculates the difference between two times
        // I learned about java.time in class - it is much better than old Date class
        Duration duration = Duration.between(vehicle.getEntryTime(), exitTime);
        long totalMinutes = duration.toMinutes();

        // just in case vehicle exits in less than 1 minute (like during testing)
        if (totalMinutes <= 0) totalMinutes = 1;

        // convert minutes to hours as decimal number
        // example: 90 minutes = 1.5 hours
        double hoursParked = (double) totalMinutes / 60.0;

        // minimum 1 hour charge - common rule in parking lots
        if (hoursParked < 1.0) hoursParked = 1.0;

        // vehicle.getHourlyRate() uses Polymorphism here
        // car returns 60, motorcycle returns 30, truck returns 100
        // we don't need if-else - Java figures it out automatically
        double fee = hoursParked * vehicle.getHourlyRate();

        // round to 2 decimal places so receipt looks clean
        return Math.round(fee * 100.0) / 100.0;
    }
}
