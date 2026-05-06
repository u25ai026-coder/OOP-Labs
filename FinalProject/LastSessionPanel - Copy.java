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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;

// LastSessionPanel displays stats from the previous application session
// Data is read from the last_session table in the database
// If no previous session exists, a message is shown instead

public class LastSessionPanel extends JPanel {

    private static final Color PANEL_BG  = new Color(243, 245, 248);
    private static final Color TABLE_HDR = new Color(35, 45, 65);
    private static final Color TABLE_ALT = new Color(248, 250, 253);

    private ParkingLot parkingLot;

    private JLabel sessionStartLabel;
    private JLabel sessionEndLabel;
    private JLabel statsLabel;
    private JLabel noDataLabel;

    private DefaultTableModel tableModel;
    private JScrollPane tableScroll;

    private static final int ROW_CAR        = 0;
    private static final int ROW_MOTORCYCLE = 1;
    private static final int ROW_TRUCK      = 2;
    private static final int ROW_TOTAL      = 3;

    public LastSessionPanel(ParkingLot parkingLot) {
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

        JLabel title = new JLabel("Last Session Summary  [Owner]");
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

        sessionStartLabel = makeInfoLabel("Session Started : —");
        sessionEndLabel   = makeInfoLabel("Session Ended   : —");
        statsLabel        = makeInfoLabel("Total Parked : 0   |   Checked Out : 0");

        content.add(sessionStartLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(sessionEndLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(statsLabel);
        content.add(Box.createRigidArea(new Dimension(0, 22)));

        noDataLabel = new JLabel("No previous session data found. Close and reopen the app to record a session.");
        noDataLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        noDataLabel.setForeground(new Color(130, 140, 160));
        noDataLabel.setAlignmentX(LEFT_ALIGNMENT);
        noDataLabel.setVisible(false);
        content.add(noDataLabel);

        // ---- TABLE ----
        String[] columns = { "Vehicle Type", "Count", "Cash (Rs.)", "Online (Rs.)", "Total (Rs.)" };

        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

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

        table.getColumnModel().getColumn(0).setPreferredWidth(140);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);

        tableScroll = new JScrollPane(table);
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

    public void refresh() {
        ResultSet rs = parkingLot.getDb().loadLastSession();
        if (rs == null) {
            showNoData();
            return;
        }
        try {
            if (!rs.next()) {
                showNoData();
                return;
            }

            String start         = rs.getString("session_start");
            String end           = rs.getString("session_end");
            int totalParked      = rs.getInt("total_parked");
            int totalCheckedOut  = rs.getInt("total_checked_out");
            int carsServed       = rs.getInt("cars_served");
            double cashCars      = rs.getDouble("cash_cars");
            double onlineCars    = rs.getDouble("online_cars");
            int motosServed      = rs.getInt("motos_served");
            double cashMotos     = rs.getDouble("cash_motos");
            double onlineMotos   = rs.getDouble("online_motos");
            int trucksServed     = rs.getInt("trucks_served");
            double cashTrucks    = rs.getDouble("cash_trucks");
            double onlineTrucks  = rs.getDouble("online_trucks");

            sessionStartLabel.setText("Session Started : " + start);
            sessionEndLabel.setText  ("Session Ended   : " + end);
            statsLabel.setText(String.format(
                "Total Parked : %d   |   Checked Out : %d",
                totalParked, totalCheckedOut));

            updateRow(ROW_CAR,        "Car",        carsServed,   cashCars,   onlineCars);
            updateRow(ROW_MOTORCYCLE, "Motorcycle", motosServed,  cashMotos,  onlineMotos);
            updateRow(ROW_TRUCK,      "Truck",      trucksServed, cashTrucks, onlineTrucks);
            updateRow(ROW_TOTAL,      "TOTAL",
                carsServed + motosServed + trucksServed,
                cashCars + cashMotos + cashTrucks,
                onlineCars + onlineMotos + onlineTrucks);

            noDataLabel.setVisible(false);
            tableScroll.setVisible(true);

        } catch (Exception e) {
            showNoData();
            System.out.println("  [DB ERROR] Failed to read last session: " + e.getMessage());
        }
    }

    private void showNoData() {
        sessionStartLabel.setText("Session Started : —");
        sessionEndLabel.setText  ("Session Ended   : —");
        statsLabel.setText("Total Parked : 0   |   Checked Out : 0");
        noDataLabel.setVisible(true);
        tableScroll.setVisible(false);
    }

    private void updateRow(int row, String label, int count, double cash, double online) {
        tableModel.setValueAt(label,                              row, 0);
        tableModel.setValueAt(count,                             row, 1);
        tableModel.setValueAt(String.format("%.2f", cash),      row, 2);
        tableModel.setValueAt(String.format("%.2f", online),    row, 3);
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
