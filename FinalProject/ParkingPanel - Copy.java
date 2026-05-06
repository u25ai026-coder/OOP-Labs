package com.smartparking;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

// ParkingPanel is the "Park a Vehicle" screen
// user selects vehicle type from a dropdown, enters plate and color,
// clicks Park, and the receipt appears in a text area below
// I used a JComboBox for vehicle type instead of a text field
// so users can't type wrong values like "caar" or leave it blank

public class ParkingPanel extends JPanel {

    private static final Color PANEL_BG   = new Color(243, 245, 248);
    private static final Color FORM_BG    = Color.WHITE;
    private static final Color BTN_COLOR  = new Color(39, 174, 96);
    private static final Color BTN_HOVER  = new Color(30, 140, 75);
    private static final Color ERR_COLOR  = new Color(192, 57, 43);

    private ParkingLot  parkingLot;
    private MainWindow  mainWindow;

    private JComboBox<String> typeCombo;
    private JTextField plateField;
    private JTextField colorField;
    private JTextArea  receiptArea;
    private JLabel     feedbackLabel;
    private JButton    printBtn;

    public ParkingPanel(ParkingLot parkingLot, MainWindow mainWindow) {
        this.parkingLot = parkingLot;
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        setBackground(PANEL_BG);
        buildUI();
    }

    private void buildUI() {

        // ---- TOP HEADER ----
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 225)));

        JLabel title = new JLabel("Park a Vehicle");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(35, 45, 65));
        header.add(title);

        add(header, BorderLayout.NORTH);

        // ---- MAIN CONTENT ----
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(PANEL_BG);
        content.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // ---- FORM CARD ----
        JPanel formCard = new JPanel(new GridLayout(4, 2, 10, 14));
        formCard.setBackground(FORM_BG);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 222, 228), 1),
            BorderFactory.createEmptyBorder(22, 24, 22, 24)
        ));
        formCard.setMaximumSize(new Dimension(520, 200));
        formCard.setAlignmentX(LEFT_ALIGNMENT);

        // row 1: vehicle type dropdown
        formCard.add(makeFormLabel("Vehicle Type"));
        typeCombo = new JComboBox<>(VehicleFactory.getSupportedTypes());
        typeCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        typeCombo.setBackground(Color.WHITE);
        formCard.add(typeCombo);

        // row 2: number plate
        formCard.add(makeFormLabel("Number Plate"));
        plateField = new JTextField();
        plateField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        formCard.add(plateField);

        // row 3: color
        formCard.add(makeFormLabel("Color"));
        colorField = new JTextField();
        colorField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        formCard.add(colorField);

        // row 4: feedback message + park button
        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        feedbackLabel.setForeground(ERR_COLOR);
        formCard.add(feedbackLabel);

        JButton parkBtn = new JButton("Park Vehicle");
        parkBtn.setBackground(BTN_COLOR);
        parkBtn.setForeground(Color.WHITE);
        parkBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        parkBtn.setFocusPainted(false);
        parkBtn.setBorderPainted(false);
        parkBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        parkBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { parkBtn.setBackground(BTN_HOVER); }
            public void mouseExited(java.awt.event.MouseEvent e)  { parkBtn.setBackground(BTN_COLOR); }
        });
        parkBtn.addActionListener(e -> handlePark());
        formCard.add(parkBtn);

        content.add(formCard);
        content.add(Box.createRigidArea(new Dimension(0, 22)));

        // ---- RECEIPT AREA ----
        JPanel receiptHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        receiptHeader.setBackground(PANEL_BG);
        receiptHeader.setAlignmentX(LEFT_ALIGNMENT);
        receiptHeader.setMaximumSize(new Dimension(520, 30));

        JLabel receiptTitle = new JLabel("Receipt");
        receiptTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        receiptTitle.setForeground(new Color(50, 60, 80));
        receiptHeader.add(receiptTitle);

        receiptHeader.add(Box.createRigidArea(new Dimension(12, 0)));

        printBtn = new JButton("Print & Clear");
        printBtn.setFont(new Font("SansSerif", Font.BOLD, 11));
        printBtn.setBackground(new Color(52, 152, 219));
        printBtn.setForeground(Color.WHITE);
        printBtn.setFocusPainted(false);
        printBtn.setBorderPainted(false);
        printBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        printBtn.setVisible(false);
        printBtn.addActionListener(e -> {
            receiptArea.setText("Receipt will appear here after parking a vehicle.");
            printBtn.setVisible(false);
        });
        receiptHeader.add(printBtn);

        content.add(receiptHeader);
        content.add(Box.createRigidArea(new Dimension(0, 8)));

        // monospaced font so the receipt lines align neatly - same as the old console output
        receiptArea = new JTextArea(13, 45);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setEditable(false);
        receiptArea.setBackground(new Color(250, 250, 252));
        receiptArea.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        receiptArea.setText("Receipt will appear here after parking a vehicle.");

        JScrollPane scroll = new JScrollPane(receiptArea);
        scroll.setMaximumSize(new Dimension(520, 240));
        scroll.setAlignmentX(LEFT_ALIGNMENT);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 222, 228), 1));
        content.add(scroll);

        add(new JScrollPane(content) {{
            setBorder(null);
            getVerticalScrollBar().setUnitIncrement(10);
        }}, BorderLayout.CENTER);
    }

    // called when Park Vehicle button is clicked
    private void handlePark() {
        String type  = (String) typeCombo.getSelectedItem();
        String plate = plateField.getText().trim().toUpperCase();
        String color = colorField.getText().trim();

        // basic validation before sending to ParkingLot
        if (plate.isEmpty()) {
            feedbackLabel.setText("Number plate cannot be empty.");
            return;
        }
        if (color.isEmpty()) {
            feedbackLabel.setText("Color cannot be empty.");
            return;
        }

        feedbackLabel.setText(" ");  // clear old error

        Vehicle vehicle = VehicleFactory.createVehicle(type, plate, color);
        if (vehicle == null) {
            feedbackLabel.setText("Invalid vehicle type selected.");
            return;
        }

        try {
            String receipt = parkingLot.parkVehicle(vehicle);
            receiptArea.setText(receipt);
            printBtn.setVisible(true);

            // clear form fields after successful park
            plateField.setText("");
            colorField.setText("");

            // refresh the dashboard map in background so it reflects the new vehicle
            mainWindow.refreshDashboard();

        } catch (ParkingFullException ex) {
            feedbackLabel.setText(ex.getMessage());
            receiptArea.setText("");
        }
    }

    private JLabel makeFormLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(new Color(60, 70, 90));
        return lbl;
    }
}
