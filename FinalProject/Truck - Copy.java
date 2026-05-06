package com.smartparking;

// Truck extends Vehicle - Inheritance
// Truck is the biggest vehicle so it needs LARGE slot
// highest rate - Rs.100 per hour because it takes maximum space

public class Truck extends Vehicle {

    private static final double HOURLY_RATE = 100.0;

    public Truck(String numberPlate, String color) {
        super(numberPlate, color, SlotType.LARGE);
    }

    @Override
    public double getHourlyRate() { return HOURLY_RATE; }

    @Override
    public String getVehicleTypeName() { return "Truck"; }
}
