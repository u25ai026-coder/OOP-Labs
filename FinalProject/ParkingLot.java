package com.smartparking;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// ParkingLot is a Singleton class
// Singleton means only ONE object of this class can exist at a time
// This is important because we only have one parking lot
// If we make two objects the slot data will get mixed up
//
// How Singleton works:
// Step 1 - make the constructor private so no one can do "new ParkingLot()"
// Step 2 - store one static object inside the class itself
// Step 3 - give a public method getInstance() to return that one object
//
// Changes I made for the GUI version:
// - added DatabaseManager so slot data is saved and reloaded between runs
// - parkVehicle() and unpark methods now return a receipt String
//   instead of printing to console - the GUI panels will display the receipt
// - added getters so the dashboard panel can read slot and session data

public class ParkingLot {

    private static ParkingLot instance = null;

    private String lotName;

    private ArrayList<ParkingSlot> smallSlots;   // M1 to M20 - motorcycles
    private ArrayList<ParkingSlot> mediumSlots;  // C1 to C10 - cars
    private ArrayList<ParkingSlot> largeSlots;   // T1 to T3  - trucks

    private Map<String, ParkingSlot> plateToSlotMap;
    private Map<String, ParkingSlot> slotIdToSlotMap;

    private ParkingFeeStrategy feeStrategy;
    private DatabaseManager db;

    // session tracking counters
    private int totalParkedToday;
    private int totalCheckedOut;
    private int carsServed, motorcyclesServed, trucksServed;
    private double cashFromCars, cashFromMotorcycles, cashFromTrucks;
    private double onlineFromCars, onlineFromMotorcycles, onlineFromTrucks;
    private LocalDateTime sessionStartTime;

    private ParkingLot(String lotName, int smallCount, int mediumCount, int largeCount) {
        this.lotName         = lotName;
        this.smallSlots      = new ArrayList<>();
        this.mediumSlots     = new ArrayList<>();
        this.largeSlots      = new ArrayList<>();
        this.plateToSlotMap  = new HashMap<>();
        this.slotIdToSlotMap = new HashMap<>();
        this.feeStrategy     = new HourlyRateStrategy();
        this.sessionStartTime = LocalDateTime.now();

        totalParkedToday = 0; totalCheckedOut = 0;
        carsServed = 0; motorcyclesServed = 0; trucksServed = 0;
        cashFromCars = 0; cashFromMotorcycles = 0; cashFromTrucks = 0;
        onlineFromCars = 0; onlineFromMotorcycles = 0; onlineFromTrucks = 0;

        // create motorcycle slots M1, M2 ... M20
        for (int i = 1; i <= smallCount; i++) {
            String id = "M" + i;
            ParkingSlot slot = new ParkingSlot(id, SlotType.SMALL);
            smallSlots.add(slot);
            slotIdToSlotMap.put(id, slot);
        }

        // create car slots C1, C2 ... C10
        for (int i = 1; i <= mediumCount; i++) {
            String id = "C" + i;
            ParkingSlot slot = new ParkingSlot(id, SlotType.MEDIUM);
            mediumSlots.add(slot);
            slotIdToSlotMap.put(id, slot);
        }

        // create truck slots T1, T2, T3
        for (int i = 1; i <= largeCount; i++) {
            String id = "T" + i;
            ParkingSlot slot = new ParkingSlot(id, SlotType.LARGE);
            largeSlots.add(slot);
            slotIdToSlotMap.put(id, slot);
        }

        // connect to DB and restore any vehicles from the last session
        this.db = new DatabaseManager();
        loadFromDatabase();
    }

    public static ParkingLot getInstance(String lotName, int small, int medium, int large) {
        if (instance == null) {
            instance = new ParkingLot(lotName, small, medium, large);
        }
        return instance;
    }

    // reads previously parked vehicles from DB and restores them into their slots
    // I struggled a bit with parsing LocalDateTime back from a string
    // turns out LocalDateTime.parse() works fine if you saved it with .toString()
    // because .toString() gives ISO format like "2026-03-30T10:45:30"
    private void loadFromDatabase() {
        ResultSet rs = db.loadAllParkedVehicles();
        if (rs == null) return;

        int count = 0;
        try {
            while (rs.next()) {
                String slotId    = rs.getString("slot_id");
                String type      = rs.getString("vehicle_type");   // e.g. "CAR"
                String plate     = rs.getString("number_plate");
                String color     = rs.getString("color");
                String entryStr  = rs.getString("entry_time");

                // recreate the vehicle using VehicleFactory
                Vehicle vehicle = VehicleFactory.createVehicle(type, plate, color);
                if (vehicle == null) continue;

                // parse the saved time string back to LocalDateTime
                LocalDateTime entryTime = LocalDateTime.parse(entryStr);
                vehicle.setEntryTime(entryTime);

                // put vehicle back into its slot
                ParkingSlot slot = slotIdToSlotMap.get(slotId);
                if (slot != null && slot.isEmpty()) {
                    slot.assignVehicle(vehicle);
                    plateToSlotMap.put(plate, slot);
                    totalParkedToday++;
                    count++;
                }
            }
            if (count > 0) {
                System.out.println("  [DB] Restored " + count + " vehicle(s) from last session.");
            }
        } catch (Exception e) {
            System.out.println("  [DB ERROR] Failed to restore vehicles: " + e.getMessage());
        }
    }

    // PARK VEHICLE
    // now returns a String receipt instead of printing it
    // the GUI panel will show this in a JTextArea
    public String parkVehicle(Vehicle vehicle) throws ParkingFullException {

        if (plateToSlotMap.containsKey(vehicle.getNumberPlate())) {
            return "[ERROR] This vehicle is already parked inside!";
        }

        ArrayList<ParkingSlot> targetSlots = getSlotsForType(vehicle.getRequiredSlotType());

        ParkingSlot availableSlot = null;
        for (int i = 0; i < targetSlots.size(); i++) {
            if (targetSlots.get(i).isEmpty()) {
                availableSlot = targetSlots.get(i);
                break;
            }
        }

        if (availableSlot == null) {
            throw new ParkingFullException("Sorry! No " + vehicle.getRequiredSlotType() + " slot available.");
        }

        vehicle.setEntryTime(LocalDateTime.now());
        availableSlot.assignVehicle(vehicle);
        plateToSlotMap.put(vehicle.getNumberPlate(), availableSlot);
        totalParkedToday++;

        // save to database
        // storing vehicle type as uppercase e.g. "CAR" so VehicleFactory can recreate it on reload
        db.saveParkedVehicle(
            availableSlot.getSlotId(),
            vehicle.getVehicleTypeName().toUpperCase(),
            vehicle.getNumberPlate(),
            vehicle.getColor(),
            vehicle.getEntryTime().toString()
        );

        return buildParkingReceipt(vehicle, availableSlot);
    }

    private String buildParkingReceipt(Vehicle vehicle, ParkingSlot slot) {
        StringBuilder sb = new StringBuilder();
        sb.append("==========================================\n");
        sb.append("          PARKING RECEIPT               \n");
        sb.append("==========================================\n");
        sb.append("Parking Lot  : ").append(lotName).append("\n");
        sb.append("------------------------------------------\n");
        sb.append("Vehicle Type : ").append(vehicle.getVehicleTypeName()).append("\n");
        sb.append("Number Plate : ").append(vehicle.getNumberPlate()).append("\n");
        sb.append("Color        : ").append(vehicle.getColor()).append("\n");
        sb.append("Slot Assigned: ").append(slot.getSlotId()).append("\n");
        sb.append("Entry Time   : ").append(vehicle.getFormattedEntryTime()).append("\n");
        sb.append("Hourly Rate  : Rs.").append(vehicle.getHourlyRate()).append(" per hour\n");
        sb.append("------------------------------------------\n");
        sb.append("Please keep this for checkout.\n");
        sb.append("==========================================\n");
        return sb.toString();
    }

    // UNPARK BY NUMBER PLATE - returns checkout receipt
    public String unparkByNumberPlate(String numberPlate, String paymentMode)
            throws VehicleNotFoundException {
        String plate = numberPlate.trim().toUpperCase();
        if (!plateToSlotMap.containsKey(plate)) {
            throw new VehicleNotFoundException("[ERROR] No vehicle found with plate: " + plate);
        }
        ParkingSlot slot = plateToSlotMap.get(plate);
        slot.getCurrentVehicle().setPaymentMode(paymentMode);
        String receipt = processCheckout(slot);
        plateToSlotMap.remove(plate);
        return receipt;
    }

    // UNPARK BY SLOT ID like M1, C3, T2 - returns checkout receipt
    public String unparkBySlotId(String slotId, String paymentMode)
            throws VehicleNotFoundException {
        String id = slotId.trim().toUpperCase();
        if (!slotIdToSlotMap.containsKey(id)) {
            throw new VehicleNotFoundException("[ERROR] Slot '" + id + "' does not exist. Valid: M1-M20, C1-C10, T1-T3");
        }
        ParkingSlot slot = slotIdToSlotMap.get(id);
        if (slot.isEmpty()) {
            throw new VehicleNotFoundException("[ERROR] Slot " + id + " is empty. No vehicle to unpark.");
        }
        String plate = slot.getCurrentVehicle().getNumberPlate();
        slot.getCurrentVehicle().setPaymentMode(paymentMode);
        String receipt = processCheckout(slot);
        plateToSlotMap.remove(plate);
        return receipt;
    }

    // calculates fee, updates counters, deletes from DB, returns checkout receipt string
    private String processCheckout(ParkingSlot slot) {
        Vehicle vehicle  = slot.getCurrentVehicle();
        LocalDateTime exitTime = LocalDateTime.now();
        double fee       = feeStrategy.calculateFee(vehicle, exitTime);
        String type      = vehicle.getVehicleTypeName();
        String mode      = vehicle.getPaymentMode();

        // update session counters based on vehicle type and payment mode
        if (type.equals("Car")) {
            carsServed++;
            if (mode.equals("CASH")) cashFromCars += fee;
            else onlineFromCars += fee;
        } else if (type.equals("Motorcycle")) {
            motorcyclesServed++;
            if (mode.equals("CASH")) cashFromMotorcycles += fee;
            else onlineFromMotorcycles += fee;
        } else if (type.equals("Truck")) {
            trucksServed++;
            if (mode.equals("CASH")) cashFromTrucks += fee;
            else onlineFromTrucks += fee;
        }
        totalCheckedOut++;

        // remove from database before freeing the slot
        db.deleteParkedVehicle(slot.getSlotId());
        slot.removeVehicle();

        return buildCheckoutReceipt(vehicle, slot, exitTime, fee);
    }

    private String buildCheckoutReceipt(Vehicle vehicle, ParkingSlot slot,
                                         LocalDateTime exitTime, double fee) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("==========================================\n");
        sb.append("         CHECKOUT RECEIPT               \n");
        sb.append("==========================================\n");
        sb.append("Vehicle Type : ").append(vehicle.getVehicleTypeName()).append("\n");
        sb.append("Number Plate : ").append(vehicle.getNumberPlate()).append("\n");
        sb.append("Color        : ").append(vehicle.getColor()).append("\n");
        sb.append("Slot         : ").append(slot.getSlotId()).append("\n");
        sb.append("Entry Time   : ").append(vehicle.getFormattedEntryTime()).append("\n");
        sb.append("Exit Time    : ").append(exitTime.format(fmt)).append("\n");
        sb.append("Hourly Rate  : Rs.").append(vehicle.getHourlyRate()).append(" per hour\n");
        sb.append("Payment Mode : ").append(vehicle.getPaymentMode()).append("\n");
        sb.append("------------------------------------------\n");
        sb.append("Total Fee    : Rs.").append(fee).append("\n");
        sb.append("Payment      : PAID\n");
        sb.append("==========================================\n");
        sb.append("     Thank you! Drive safe :)            \n");
        sb.append("==========================================\n");
        return sb.toString();
    }

    // -------------------------------------------------------
    // GETTERS for GUI panels
    // -------------------------------------------------------

    public String getLotName()   { return lotName; }

    public ArrayList<ParkingSlot> getSmallSlots()  { return smallSlots; }
    public ArrayList<ParkingSlot> getMediumSlots() { return mediumSlots; }
    public ArrayList<ParkingSlot> getLargeSlots()  { return largeSlots; }

    public int getTotalParkedToday()     { return totalParkedToday; }
    public int getTotalCheckedOut()      { return totalCheckedOut; }
    public int getCurrentlyParkedCount() { return plateToSlotMap.size(); }

    public int getCarsServed()        { return carsServed; }
    public int getMotorcyclesServed() { return motorcyclesServed; }
    public int getTrucksServed()      { return trucksServed; }

    public double getCashFromCars()          { return cashFromCars; }
    public double getOnlineFromCars()        { return onlineFromCars; }
    public double getCashFromMotorcycles()   { return cashFromMotorcycles; }
    public double getOnlineFromMotorcycles() { return onlineFromMotorcycles; }
    public double getCashFromTrucks()        { return cashFromTrucks; }
    public double getOnlineFromTrucks()      { return onlineFromTrucks; }

    public LocalDateTime getSessionStartTime() { return sessionStartTime; }

    public DatabaseManager getDb() { return db; }

    // called on window close to persist this session's stats for the "Last Session" panel
    public void saveSessionToDatabase() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        db.saveLastSession(
            sessionStartTime.format(fmt),
            LocalDateTime.now().format(fmt),
            totalParkedToday, totalCheckedOut,
            carsServed, cashFromCars, onlineFromCars,
            motorcyclesServed, cashFromMotorcycles, onlineFromMotorcycles,
            trucksServed, cashFromTrucks, onlineFromTrucks
        );
    }

    // -------------------------------------------------------

    private ArrayList<ParkingSlot> getSlotsForType(SlotType slotType) {
        if (slotType == SlotType.SMALL)  return smallSlots;
        if (slotType == SlotType.MEDIUM) return mediumSlots;
        return largeSlots;
    }
}
