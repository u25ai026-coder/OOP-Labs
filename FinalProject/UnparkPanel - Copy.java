package com.smartparking;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

// UnparkPanel is the "Checkout" screen
// user enters a slot ID (like M3, C7) or a number plate
// selects cash or online payment, then clicks checkout
// the receipt shows in the text area below
// I reused the same isSlotId() logic from the old Main.java here

public class UnparkPanel extends JPanel {

    private static final Color PANEL_BG  = new Color(243, 245, 248);
    private static final Color FORM_BG   = Color.WHITE;
    private static final Color BTN_COLOR = new Color(41, 128, 185);
    private static final Color BTN_HOVER = new Color(30, 100, 150);
    private static final Color ERR_COLOR = new Color(192, 57, 43);

    private ParkingLot parkingLot;
    private MainWindow mainWindow;

    private JTextField    inputField;
    private JRadioButton  cashRadio;
    private JRadioButton  onlineRadio;
    private JTextArea     receiptArea;
    private JLabel        feedbackLabel;
    private JButton       printBtn;

    public UnparkPanel(ParkingLot parkingLot, MainWindow mainWindow) {
        this.parkingLot = parkingLot;
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        setBackground(PANEL_BG);
        buildUI();
    }

    private void buildUI() {

        // ---- HEADER ----
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 225)));

        JLabel title = new JLabel("Unpark / Checkout");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(35, 45, 65));
        header.add(title);

        add(header, BorderLayout.NORTH);

        // ---- CONTENT ----
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(PANEL_BG);
        content.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // ---- FORM CARD ----
        // 3 rows: input, payment mode, buttons row
        JPanel formCard = new JPanel(new GridLayout(3, 2, 10, 16));
        formCard.setBackground(FORM_BG);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 222, 228), 1),
            BorderFactory.createEmptyBorder(22, 24, 22, 24)
        ));
        formCard.setMaximumSize(new Dimension(520, 160));
        formCard.setAlignmentX(LEFT_ALIGNMENT);

        // row 1: input field
        JLabel inputLabel = new JLabel("Slot ID or Number Plate");
        inputLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        inputLabel.setForeground(new Color(60, 70, 90));
        formCard.add(inputLabel);

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        inputField.setToolTipText("e.g. M3, C7, T1  or  GJ11S1234");
        formCard.add(inputField);

        // row 2: payment mode radio buttons
        JLabel payLabel = new JLabel("Payment Mode");
        payLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        payLabel.setForeground(new Color(60, 70, 90));
        formCard.add(payLabel);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        radioPanel.setBackground(FORM_BG);
        cashRadio   = new JRadioButton("Cash");
        onlineRadio = new JRadioButton("Online");
        cashRadio.setSelected(true);  // default to cash
        cashRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        onlineRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cashRadio.setBackground(FORM_BG);
        onlineRadio.setBackground(FORM_BG);

        // ButtonGroup makes sure only one can be selected at a time
        ButtonGroup group = new ButtonGroup();
        group.add(cashRadio);
        group.add(onlineRadio);

        radioPanel.add(cashRadio);
        radioPanel.add(onlineRadio);
        formCard.add(radioPanel);

        // row 3: feedback label + checkout button
        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        feedbackLabel.setForeground(ERR_COLOR);
        formCard.add(feedbackLabel);

        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.setBackground(BTN_COLOR);
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        checkoutBtn.setFocusPainted(false);
        checkoutBtn.setBorderPainted(false);
        checkoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { checkoutBtn.setBackground(BTN_HOVER); }
            public void mouseExited(java.awt.event.MouseEvent e)  { checkoutBtn.setBackground(BTN_COLOR); }
        });
        checkoutBtn.addActionListener(e -> handleUnpark());
        formCard.add(checkoutBtn);

        content.add(formCard);
        content.add(Box.createRigidArea(new Dimension(0, 8)));

        // small hint label below the form
        JLabel hint = new JLabel("  Hint: enter a slot ID like M3 or C7, or the vehicle's number plate.");
        hint.setFont(new Font("SansSerif", Font.ITALIC, 11));
        hint.setForeground(new Color(130, 140, 160));
        hint.setAlignmentX(LEFT_ALIGNMENT);
        content.add(hint);
        content.add(Box.createRigidArea(new Dimension(0, 20)));

        // ---- RECEIPT AREA ----
        JPanel receiptHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        receiptHeader.setBackground(PANEL_BG);
        receiptHeader.setAlignmentX(LEFT_ALIGNMENT);
        receiptHeader.setMaximumSize(new Dimension(520, 30));

        JLabel receiptTitle = new JLabel("Checkout Receipt");
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
            receiptArea.setText("Checkout receipt will appear here.");
            printBtn.setVisible(false);
        });
        receiptHeader.add(printBtn);

        content.add(receiptHeader);
        content.add(Box.createRigidArea(new Dimension(0, 8)));

        receiptArea = new JTextArea(13, 45);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setEditable(false);
        receiptArea.setBackground(new Color(250, 250, 252));
        receiptArea.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        receiptArea.setText("Checkout receipt will appear here.");

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

    private void handleUnpark() {
        String input = inputField.getText().trim().toUpperCase();

        if (input.isEmpty()) {
            feedbackLabel.setText("Please enter a slot ID or number plate.");
            return;
        }

        feedbackLabel.setText(" ");

        String paymentMode = cashRadio.isSelected() ? "CASH" : "ONLINE";

        try {
            String receipt;
            // detect if user entered a slot id (M1, C3, T2) or a number plate
            if (isSlotId(input)) {
                receipt = parkingLot.unparkBySlotId(input, paymentMode);
            } else {
                receipt = parkingLot.unparkByNumberPlate(input, paymentMode);
            }

            receiptArea.setText(receipt);
            printBtn.setVisible(true);
            inputField.setText("");
            cashRadio.setSelected(true);  // reset payment selection

            // refresh map so freed slot turns green
            mainWindow.refreshDashboard();

        } catch (VehicleNotFoundException ex) {
            feedbackLabel.setText(ex.getMessage());
            receiptArea.setText("");
        }
    }

    // checks if input looks like a slot ID - M1, C3, T2 etc.
    // copied the same logic from old Main.java so behavior stays the same
    private boolean isSlotId(String input) {
        if (input.length() < 2) return false;
        char first = input.charAt(0);
        String rest = input.substring(1);
        return (first == 'M' || first == 'C' || first == 'T') && isAllDigits(rest);
    }

    private boolean isAllDigits(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}
