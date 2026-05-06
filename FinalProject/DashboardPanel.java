package com.smartparking;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

// DashboardPanel shows the visual parking map
// each slot is a colored box - green means empty, red means occupied
// this is the "home page" equivalent in our website-like layout
// I got the idea of using colored JLabels as slot boxes from a Swing tutorial
// much simpler than drawing on canvas

public class DashboardPanel extends JPanel {

    private static final Color GREEN_FREE     = new Color(39, 174, 96);
    private static final Color RED_OCCUPIED   = new Color(192, 57, 43);
    private static final Color PANEL_BG       = new Color(243, 245, 248);
    private static final Color SECTION_HEADER = new Color(60, 70, 90);

    private ParkingLot parkingLot;

    // these three grids hold the slot boxes - refreshed every time we call refresh()
    private JPanel motoGrid;
    private JPanel carGrid;
    private JPanel truckGrid;

    // status bar at the top showing counts
    private JLabel statusLine;

    public DashboardPanel(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        setLayout(new BorderLayout());
        setBackground(PANEL_BG);
        buildUI();
    }

    private void buildUI() {

        // ---- TOP HEADER BAR ----
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 225)));

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(35, 45, 65));
        header.add(title);

        // refresh button - lets user manually update the map
        JButton refreshBtn = new JButton("Refresh Map");
        refreshBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        refreshBtn.setBackground(new Color(52, 152, 219));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> refresh());
        header.add(refreshBtn);

        add(header, BorderLayout.NORTH);

        // ---- SCROLLABLE CONTENT AREA ----
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(PANEL_BG);
        content.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        // status line showing slot counts
        statusLine = new JLabel("Loading...");
        statusLine.setFont(new Font("SansSerif", Font.PLAIN, 13));
        statusLine.setForeground(new Color(80, 90, 110));
        statusLine.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        statusLine.setAlignmentX(LEFT_ALIGNMENT);
        content.add(statusLine);

        // ---- LEGEND ----
        JPanel legend = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        legend.setBackground(PANEL_BG);
        legend.setAlignmentX(LEFT_ALIGNMENT);
        legend.add(makeLegendBox(GREEN_FREE, "FREE"));
        legend.add(makeLegendBox(RED_OCCUPIED, "OCCUPIED"));
        legend.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        content.add(legend);

        // ---- MOTORCYCLE SLOTS ----
        // 16 slots, 2 rows of 8 - I used GridLayout so it stays fixed and doesn't wrap randomly
        content.add(makeSectionLabel("Motorcycle Slots  (M1 – M16)"));
        content.add(Box.createRigidArea(new Dimension(0, 6)));
        motoGrid = new JPanel(new GridLayout(2, 8, 6, 6));
        motoGrid.setBackground(PANEL_BG);
        motoGrid.setAlignmentX(LEFT_ALIGNMENT);
        motoGrid.setMaximumSize(new Dimension(762, 106));
        content.add(motoGrid);
        content.add(Box.createRigidArea(new Dimension(0, 16)));

        // ---- CAR SLOTS ----
        // 8 slots, 1 row of 8
        content.add(makeSectionLabel("Car Slots  (C1 – C8)"));
        content.add(Box.createRigidArea(new Dimension(0, 6)));
        carGrid = new JPanel(new GridLayout(1, 8, 6, 6));
        carGrid.setBackground(PANEL_BG);
        carGrid.setAlignmentX(LEFT_ALIGNMENT);
        carGrid.setMaximumSize(new Dimension(762, 50));
        content.add(carGrid);
        content.add(Box.createRigidArea(new Dimension(0, 16)));

        // ---- TRUCK SLOTS ----
        // only 3 slots so I kept the same size box as others
        content.add(makeSectionLabel("Truck Slots  (T1 – T3)"));
        content.add(Box.createRigidArea(new Dimension(0, 6)));
        truckGrid = new JPanel(new GridLayout(1, 3, 6, 6));
        truckGrid.setBackground(PANEL_BG);
        truckGrid.setAlignmentX(LEFT_ALIGNMENT);
        truckGrid.setMaximumSize(new Dimension(282, 50));
        content.add(truckGrid);

        // no scroll pane needed - all slots fit in one screen now
        add(content, BorderLayout.CENTER);

        // fill the slot boxes for the first time
        refresh();
    }

    // rebuilds all slot boxes with current data
    // called on startup, when nav button is clicked, and after park/unpark
    public void refresh() {
        fillGrid(motoGrid,  parkingLot.getSmallSlots());
        fillGrid(carGrid,   parkingLot.getMediumSlots());
        fillGrid(truckGrid, parkingLot.getLargeSlots());
        updateStatusLine();

        // revalidate + repaint forces Swing to redraw everything
        motoGrid.revalidate();   motoGrid.repaint();
        carGrid.revalidate();    carGrid.repaint();
        truckGrid.revalidate();  truckGrid.repaint();
    }

    // clears the grid panel and redraws all slot boxes from the current slot list
    private void fillGrid(JPanel grid, ArrayList<ParkingSlot> slots) {
        grid.removeAll();
        for (ParkingSlot slot : slots) {
            JLabel box = new JLabel();
            box.setPreferredSize(new Dimension(95, 52));
            box.setHorizontalAlignment(SwingConstants.CENTER);
            box.setVerticalAlignment(SwingConstants.CENTER);
            box.setFont(new Font("SansSerif", Font.BOLD, 10));
            box.setOpaque(true);
            // small border around each box to separate them visually
            box.setBorder(BorderFactory.createLineBorder(new Color(200, 205, 215), 1));

            if (slot.isEmpty()) {
                box.setBackground(GREEN_FREE);
                box.setForeground(Color.WHITE);
                // using html so I can put slot id on top and FREE on bottom
                box.setText("<html><center><b>" + slot.getSlotId() + "</b><br><small>FREE</small></center></html>");
            } else {
                box.setBackground(RED_OCCUPIED);
                box.setForeground(Color.WHITE);
                String plate = slot.getCurrentVehicle().getNumberPlate();
                // truncate long plates so they don't overflow the box
                if (plate.length() > 9) plate = plate.substring(0, 9);
                box.setText("<html><center><b>" + slot.getSlotId() + "</b><br><small>" + plate + "</small></center></html>");
            }

            grid.add(box);
        }
    }

    // updates the status summary line at top of content area
    private void updateStatusLine() {
        ArrayList<ParkingSlot> small  = parkingLot.getSmallSlots();
        ArrayList<ParkingSlot> medium = parkingLot.getMediumSlots();
        ArrayList<ParkingSlot> large  = parkingLot.getLargeSlots();

        int mFree = 0, cFree = 0, tFree = 0;
        for (ParkingSlot s : small)  if (s.isEmpty()) mFree++;
        for (ParkingSlot s : medium) if (s.isEmpty()) cFree++;
        for (ParkingSlot s : large)  if (s.isEmpty()) tFree++;

        int total = parkingLot.getCurrentlyParkedCount();

        statusLine.setText(String.format(
            "Motorcycles: %d / %d free    |    Cars: %d / %d free    |    Trucks: %d / %d free    |    Currently Parked: %d",
            mFree, small.size(), cFree, medium.size(), tFree, large.size(), total
        ));
    }

    // helper - creates the section title labels like "Motorcycle Slots (M1 - M20)"
    private JLabel makeSectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(SECTION_HEADER);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }

    // helper - small colored square + label for the legend
    private JPanel makeLegendBox(Color color, String text) {
        JPanel box = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        box.setBackground(PANEL_BG);

        JLabel colorSquare = new JLabel("  ");
        colorSquare.setOpaque(true);
        colorSquare.setBackground(color);
        colorSquare.setPreferredSize(new Dimension(16, 16));

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        textLabel.setForeground(new Color(70, 80, 100));

        box.add(colorSquare);
        box.add(textLabel);
        return box;
    }
}
