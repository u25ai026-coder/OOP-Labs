package com.smartparking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// I added this class to save parked vehicle data in a database
// so if the program is closed and reopened, the slots are still filled
// I used SQLite because it saves everything in one .db file
// no need to install MySQL or anything like that
//
// HOW TO SET UP: download sqlite-jdbc-3.x.x.jar from GitHub (xerial/sqlite-jdbc)
// in VS Code add the path in settings.json under "java.project.referencedLibraries"
// example:  "java.project.referencedLibraries": ["lib/sqlite-jdbc-3.47.1.0.jar"]

public class DatabaseManager {

    // the .db file will be created in whatever folder you run the program from
    private static final String DB_URL = "jdbc:sqlite:smartparking.db";

    private Connection conn;

    public DatabaseManager() {
        try {
            // this line loads the SQLite JDBC driver into memory
            // without this, Java doesn't know how to talk to SQLite
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(DB_URL);
            System.out.println("  [DB] Connected to smartparking.db");

            createTableIfNotExists();

        } catch (ClassNotFoundException e) {
            // this happens if the sqlite-jdbc jar is missing from classpath
            System.out.println("  [DB ERROR] sqlite-jdbc driver not found.");
            System.out.println("             Add sqlite-jdbc jar to your classpath.");
            conn = null;
        } catch (SQLException e) {
            System.out.println("  [DB ERROR] Could not connect: " + e.getMessage());
            conn = null;
        }
    }

    // creates the table if it doesn't already exist
    // I store entry_time as TEXT because LocalDateTime has no direct SQL type
    // I'll just save it as LocalDateTime.toString() and parse it back later
    // LocalDateTime.toString() gives format like "2026-03-30T10:45:30"
    private void createTableIfNotExists() throws SQLException {
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS parked_vehicles ("
                + "slot_id      TEXT PRIMARY KEY, "
                + "vehicle_type TEXT NOT NULL, "
                + "number_plate TEXT NOT NULL, "
                + "color        TEXT NOT NULL, "
                + "entry_time   TEXT NOT NULL"
                + ");");

        // single-row table that stores the most recent completed session
        stmt.execute("CREATE TABLE IF NOT EXISTS last_session ("
                + "id               INTEGER PRIMARY KEY, "
                + "session_start    TEXT NOT NULL, "
                + "session_end      TEXT NOT NULL, "
                + "total_parked     INTEGER NOT NULL, "
                + "total_checked_out INTEGER NOT NULL, "
                + "cars_served      INTEGER NOT NULL, "
                + "cash_cars        REAL NOT NULL, "
                + "online_cars      REAL NOT NULL, "
                + "motos_served     INTEGER NOT NULL, "
                + "cash_motos       REAL NOT NULL, "
                + "online_motos     REAL NOT NULL, "
                + "trucks_served    INTEGER NOT NULL, "
                + "cash_trucks      REAL NOT NULL, "
                + "online_trucks    REAL NOT NULL"
                + ");");
    }

    // saves the current session stats; replaces any previous row (only one row kept)
    public void saveLastSession(String sessionStart, String sessionEnd,
                                int totalParked, int totalCheckedOut,
                                int carsServed, double cashCars, double onlineCars,
                                int motosServed, double cashMotos, double onlineMotos,
                                int trucksServed, double cashTrucks, double onlineTrucks) {
        if (conn == null) return;
        try {
            conn.createStatement().execute("DELETE FROM last_session;");
            String sql = "INSERT INTO last_session VALUES "
                    + "(1,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sessionStart);
            ps.setString(2, sessionEnd);
            ps.setInt(3, totalParked);
            ps.setInt(4, totalCheckedOut);
            ps.setInt(5, carsServed);
            ps.setDouble(6, cashCars);
            ps.setDouble(7, onlineCars);
            ps.setInt(8, motosServed);
            ps.setDouble(9, cashMotos);
            ps.setDouble(10, onlineMotos);
            ps.setInt(11, trucksServed);
            ps.setDouble(12, cashTrucks);
            ps.setDouble(13, onlineTrucks);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("  [DB ERROR] Could not save last session: " + e.getMessage());
        }
    }

    // returns the last_session row, or null if none exists
    public ResultSet loadLastSession() {
        if (conn == null) return null;
        try {
            return conn.createStatement()
                       .executeQuery("SELECT * FROM last_session LIMIT 1;");
        } catch (SQLException e) {
            System.out.println("  [DB ERROR] Could not load last session: " + e.getMessage());
            return null;
        }
    }

    // saves a vehicle to the database when it enters the lot
    // using INSERT OR REPLACE so if somehow the same slot is used twice it just overwrites
    public void saveParkedVehicle(String slotId, String vehicleType,
                                   String numberPlate, String color, String entryTime) {
        if (conn == null) return;  // skip if no database connection

        String sql = "INSERT OR REPLACE INTO parked_vehicles VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, slotId);
            ps.setString(2, vehicleType);
            ps.setString(3, numberPlate);
            ps.setString(4, color);
            ps.setString(5, entryTime);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("  [DB ERROR] Could not save vehicle: " + e.getMessage());
        }
    }

    // removes the vehicle record when they check out
    public void deleteParkedVehicle(String slotId) {
        if (conn == null) return;

        String sql = "DELETE FROM parked_vehicles WHERE slot_id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, slotId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("  [DB ERROR] Could not delete record: " + e.getMessage());
        }
    }

    // used at startup to reload all currently parked vehicles from the last session
    // ParkingLot will call this and loop through the ResultSet to restore slot states
    public ResultSet loadAllParkedVehicles() {
        if (conn == null) return null;
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery("SELECT * FROM parked_vehicles;");
        } catch (SQLException e) {
            System.out.println("  [DB ERROR] Could not load vehicles: " + e.getMessage());
            return null;
        }
    }

    public boolean isConnected() {
        return conn != null;
    }

    // call this when the program is closing
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("  [DB] Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("  [DB ERROR] Could not close connection: " + e.getMessage());
        }
    }
}
