package com.smartparking;

// Motorcycle extends Vehicle - Inheritance
// Motorcycle is smaller so it uses SMALL slot
// and has lower rate than car - Rs.30 per hour

public class Motorcycle extends Vehicle {

    private static final double HOURLY_RATE = 30.0;

    public Motorcycle(String numberPlate, String color) {
        super(numberPlate, color, SlotType.SMALL);
    }

    @Override
    public double getHourlyRate() { return HOURLY_RATE; }

    @Override
    public String getVehicleTypeName() { return "Motorcycle"; }
}
