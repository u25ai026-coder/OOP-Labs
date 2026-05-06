package com.smartparking;

// VehicleFactory creates Vehicle objects for us - this is the Factory Pattern
// The idea is that Main class should not worry about how Car or Truck is created
// It just says "give me a CAR" and factory handles everything
// This is also Abstraction - hiding the creation details from the caller
// If we ever change how Car is constructed, we only change it here

public class VehicleFactory {

    // static method so we can call it without creating a VehicleFactory object
    public static Vehicle createVehicle(String type, String numberPlate, String color) {

        // toUpperCase() so user can type "car" or "Car" or "CAR" - all will work
        String vehicleType = type.trim().toUpperCase();

        if (vehicleType.equals("CAR")) {
            return new Car(numberPlate.trim().toUpperCase(), color.trim());
        } else if (vehicleType.equals("MOTORCYCLE")) {
            return new Motorcycle(numberPlate.trim().toUpperCase(), color.trim());
        } else if (vehicleType.equals("TRUCK")) {
            return new Truck(numberPlate.trim().toUpperCase(), color.trim());
        } else {
            // returning null for unknown type - the caller (GUI or Main) handles this
            return null;
        }
    }

    // returns the list of types as a String array
    // used by the GUI dropdown instead of printing to console
    public static String[] getSupportedTypes() {
        return new String[] { "Car", "Motorcycle", "Truck" };
    }
}
