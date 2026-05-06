package com.smartparking;

// this class is for one single parking slot
// each slot has an id like M1 or C3, a type, and can hold one vehicle at a time

public class ParkingSlot {

    private String slotId;
    private SlotType slotType;
    private Vehicle currentVehicle;
    private boolean isEmpty;

    public ParkingSlot(String slotId, SlotType slotType) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.currentVehicle = null;
        this.isEmpty = true;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
        this.isEmpty = false;
    }

    public void removeVehicle() {
        this.currentVehicle = null;
        this.isEmpty = true;
    }

    public String getSlotId()              { return slotId; }
    public SlotType getSlotType()          { return slotType; }
    public Vehicle getCurrentVehicle()     { return currentVehicle; }
    public boolean isEmpty()               { return isEmpty; }

    // this method returns a fixed-width string for the parking map display
    // I am using String.format so every slot box is exactly the same width
    // without this, slots with short plate numbers look different from long ones
    // and the map looks really messy - I noticed this while testing
    public String getMapDisplay() {
        // max 10 characters for number plate - fits something like GJ11S1234
        // String.format pads with spaces so every box is same width
        String content;

        if (isEmpty) {
            content = "          "; // 10 spaces for empty slot
        } else {
            String plate = currentVehicle.getNumberPlate();
            if (plate.length() > 10) {
                plate = plate.substring(0, 10);
            }
            // left align and pad to 10 chars so all boxes stay same size
            content = String.format("%-10s", plate);
        }

        return String.format("| %-3s: %s |", slotId, content);
    }
}
