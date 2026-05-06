package com.smartparking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Vehicle is an abstract class
// abstract means we cannot create a Vehicle object directly
// like we cannot write "new Vehicle()" - it will give error
// instead we use Car, Motorcycle, or Truck which extend this class
// this concept is called Abstraction in OOP

public abstract class Vehicle {

    // using private so these cannot be accessed from outside directly
    // this is Encapsulation - we protect data using getters and setters
    private String numberPlate;
    private String color;
    private LocalDateTime entryTime;
    private SlotType requiredSlotType;
    private String paymentMode;  // either CASH or ONLINE

    public Vehicle(String numberPlate, String color, SlotType requiredSlotType) {
        this.numberPlate = numberPlate;
        this.color = color;
        this.requiredSlotType = requiredSlotType;
        this.entryTime = null;   // will be set when vehicle enters the parking lot
        this.paymentMode = "CASH";  // default is cash
    }

    // these two methods are abstract - means every subclass must implement them
    // Car, Motorcycle and Truck each give their own hourly rate
    // this is Polymorphism - same method name but different behavior
    public abstract double getHourlyRate();
    public abstract String getVehicleTypeName();

    // setters
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }
    public void setPaymentMode(String paymentMode)    { this.paymentMode = paymentMode; }

    // getters - used to read the private fields from outside
    public String getNumberPlate()       { return numberPlate; }
    public String getColor()             { return color; }
    public LocalDateTime getEntryTime()  { return entryTime; }
    public SlotType getRequiredSlotType(){ return requiredSlotType; }
    public String getPaymentMode()       { return paymentMode; }

    // this formats the entry time into a readable string like "30-03-2026 10:45:00"
    public String getFormattedEntryTime() {
        if (entryTime == null) return "Not parked yet";
        return entryTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
