package com.smartparking;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// Main class is the entry point
// in the old version this had a Scanner loop that kept running until user typed 6
// now it just creates the ParkingLot singleton and opens the GUI window
//
// SwingUtilities.invokeLater is the correct way to start a Swing GUI
// I learned that Swing is not thread-safe so all UI creation should happen
// on the Event Dispatch Thread (EDT) - invokeLater ensures that
//
// the ParkingLot is still a singleton with 20 motorcycle, 10 car, 3 truck slots
// same as before - only the UI changed, the core logic is untouched

public class Main {

    public static void main(String[] args) {

        // try to match the OS native look - makes it look less "Java-default"
        // on Windows this gives a slightly cleaner appearance
        // I wrapped it in try-catch because it's not critical if it fails
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // not a big deal if this doesn't work, just use default Swing look
        }

        // create parking lot first (outside EDT) so DB loads before UI shows
        // changed to 16 motorcycle, 8 car, 3 truck so everything fits in one screen
        ParkingLot parkingLot = ParkingLot.getInstance("City Center Parking", 16, 8, 3);

        // launch the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow(parkingLot);
            window.setVisible(true);
        });
    }
}
