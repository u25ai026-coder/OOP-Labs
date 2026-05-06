package com.smartparking;

// Car extends Vehicle - this is Inheritance
// Car is a type of Vehicle so it makes sense to extend it
// Car needs a MEDIUM slot and charges Rs.60 per hour

public class Car extends Vehicle {

    // I used static final because this value never changes
    // static means it belongs to the class, not to each object
    private static final double HOURLY_RATE = 60.0;

    public Car(String numberPlate, String color) {
        // calling parent class constructor using super keyword
        super(numberPlate, color, SlotType.MEDIUM);
    }

    // returning the hourly rate for car
    // this method is called using polymorphism in billing
    @Override
    public double getHourlyRate() { return HOURLY_RATE; }

    @Override
    public String getVehicleTypeName() { return "Car"; }
}
