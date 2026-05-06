package com.smartparking;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// SummaryPanel shows today's revenue and vehicle counts in a table
// this is only visible to the owner - same as option 5 in the old terminal version
// I used JTable here because the data naturally fits a table layout
// DefaultTableModel makes it easy to update rows without recreating the whole table

public class SummaryPanel extends JPanel {

    private static final Color PANEL_BG    = new Color(243, 245, 248);
    private static final Color TABLE_HDR   = new Color(35, 45, 65);
    private static final Color TABLE_ALT   = new Color(248, 250, 253);
    private static final DateTimeFormatter DISPLAY_FMT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");

    private ParkingLot parkingLot;

    // labels that show the session info above the table
    private JLabel sessionStartLabel;
    private JLabel reportTimeLabel;
    private JLabel statsLabel;

    // table model so I can call setValueAt() to update rows without rebuilding
    private DefaultTableModel tableModel;

    // index of each vehicle row in the table
    private static final int ROW_CAR        = 0;
    private static final int ROW_MOTORCYCLE = 1;
    private static final int ROW_TRUCK      = 2;
    private static final int ROW_TOTAL      = 3;

    public SummaryPanel(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        setLayout(new BorderLayout());
        setBackground(PANEL_BG);
        buildUI();
    }

    private void buildUI() {

        // ---- HEADER ----
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 225)));

        JLabel title = new JLabel("Session Summary  [Owner]");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(35, 45, 65));
        header.add(title);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        refreshBtn.setBackground(new Color(52, 152, 219));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> refresh());
        header.add(refreshBtn);

        add(header, BorderLayout.NORTH);

        // ---- CONTENT ----
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(PANEL_BG);
        content.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        // session info labels
        sessionStartLabel = makeInfoLabel("Session Started : —");
        reportTimeLabel   = makeInfoLabel("Report Time     : —");
        statsLabel        = makeInfoLabel("Total Parked : 0   |   Checked Out : 0   |   Still Inside : 0");

        content.add(sessionStartLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(reportTimeLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(statsLabel);
        content.add(Box.createRigidArea(new Dimension(0, 22)));

        // ---- TABLE ----
        String[] columns = { "Vehicle Type", "Count", "Cash (Rs.)", "Online (Rs.)", "Total (Rs.)" };

        // I used non-editable table model so users can't accidentally edit the cells
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        // add 4 rows: Car, Motorcycle, Truck, TOTAL
        tableModel.addRow(new Object[] { "Car",        0, "0.00", "0.00", "0.00" });
        tableModel.addRow(new Object[] { "Motorcycle", 0, "0.00", "0.00", "0.00" });
        tableModel.addRow(new Object[] { "Truck",      0, "0.00", "0.00", "0.00" });
        tableModel.addRow(new Object[] { "TOTAL",      0, "0.00", "0.00", "0.00" });

        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setShowGrid(true);
        table.setGridColor(new Color(225, 228, 235));
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);

        // style the header row - use a custom renderer because the Windows L&F
        // ignores setBackground/setForeground on JTableHeader otherwise
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            public java.awt.Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean focus, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
                lbl.setBackground(TABLE_HDR);
                lbl.setForeground(Color.WHITE);
                lbl.setHorizontalAlignment(col == 0 ? SwingConstants.LEFT : SwingConstants.CENTER);
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // center-align all columns except the first one (vehicle type)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // make TOTAL row bold - I manually check the row index in a custom renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public java.awt.Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                if (row == ROW_TOTAL) {
                    setFont(new Font("SansSerif", Font.BOLD, 13));
                    setBackground(new Color(235, 238, 245));
                } else {
                    setFont(new Font("SansSerif", Font.PLAIN, 13));
                    setBackground(row % 2 == 0 ? Color.WHITE : TABLE_ALT);
                }
                if (col > 0) setHorizontalAlignment(SwingConstants.CENTER);
                else         setHorizontalAlignment(SwingConstants.LEFT);
                return this;
            }
        });

        // set column widths so everything fits nicely
        table.getColumnModel().getColumn(0).setPreferredWidth(140);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setMaximumSize(new Dimension(620, 175));
        tableScroll.setAlignmentX(LEFT_ALIGNMENT);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225), 1));
        content.add(tableScroll);

        add(new JScrollPane(content) {{
            setBorder(null);
            getVerticalScrollBar().setUnitIncrement(10);
        }}, BorderLayout.CENTER);

        refresh();
    }

    // updates all labels and table rows with the latest data from ParkingLot
    public void refresh() {
        // session info
        sessionStartLabel.setText("Session Started : "
                + parkingLot.getSessionStartTime().format(DISPLAY_FMT));
        reportTimeLabel.setText("Report Time     : "
                + LocalDateTime.now().format(DISPLAY_FMT));
        statsLabel.setText(String.format(
                "Total Parked : %d   |   Checked Out : %d   |   Still Inside : %d",
                parkingLot.getTotalParkedToday(),
                parkingLot.getTotalCheckedOut(),
                parkingLot.getCurrentlyParkedCount()));

        // pull numbers from ParkingLot
        double cashCar    = parkingLot.getCashFromCars();
        double onlineCar  = parkingLot.getOnlineFromCars();
        double cashMoto   = parkingLot.getCashFromMotorcycles();
        double onlineMoto = parkingLot.getOnlineFromMotorcycles();
        double cashTruck  = parkingLot.getCashFromTrucks();
        double onlineTruck= parkingLot.getOnlineFromTrucks();

        double cashTotal   = cashCar  + cashMoto  + cashTruck;
        double onlineTotal = onlineCar+ onlineMoto + onlineTruck;
        int    totalServed = parkingLot.getCarsServed()
                           + parkingLot.getMotorcyclesServed()
                           + parkingLot.getTrucksServed();

        // update each table row
        updateRow(ROW_CAR,        "Car",        parkingLot.getCarsServed(),        cashCar,    onlineCar);
        updateRow(ROW_MOTORCYCLE, "Motorcycle", parkingLot.getMotorcyclesServed(), cashMoto,   onlineMoto);
        updateRow(ROW_TRUCK,      "Truck",      parkingLot.getTrucksServed(),      cashTruck,  onlineTruck);
        updateRow(ROW_TOTAL,      "TOTAL",      totalServed,                       cashTotal,  onlineTotal);
    }

    private void updateRow(int row, String label, int count, double cash, double online) {
        tableModel.setValueAt(label,                     row, 0);
        tableModel.setValueAt(count,                     row, 1);
        tableModel.setValueAt(String.format("%.2f", cash),    row, 2);
        tableModel.setValueAt(String.format("%.2f", online),  row, 3);
        tableModel.setValueAt(String.format("%.2f", cash + online), row, 4);
    }

    private JLabel makeInfoLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(new Color(70, 80, 100));
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }
}
