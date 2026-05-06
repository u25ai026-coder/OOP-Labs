package com.smartparking;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.CardLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// MainWindow is the main JFrame - basically the browser window of our parking system
// I went with a sidebar on the left (dark colored) and content area on the right
// the content area uses CardLayout so we can switch between screens
// it is similar to how a website changes the main content when you click nav links
// but the nav bar stays fixed - I find this cleaner than having multiple windows

public class MainWindow extends JFrame {

    // color palette for the whole UI
    // I picked dark navy for the sidebar and light gray-white for content
    private static final Color SIDEBAR_BG    = new Color(28, 37, 54);
    private static final Color NAV_BTN_BG    = new Color(40, 52, 75);
    private static final Color NAV_BTN_HOVER = new Color(60, 78, 108);
    private static final Color ACCENT_BLUE   = new Color(52, 152, 219);
    private static final Color CONTENT_BG    = new Color(243, 245, 248);

    private CardLayout cardLayout;
    private JPanel contentPanel;

    // keeping references to panels that need refreshing
    private DashboardPanel    dashboardPanel;
    private SummaryPanel      summaryPanel;
    private LastSessionPanel  lastSessionPanel;

    public MainWindow(ParkingLot parkingLot) {
        setTitle("Smart Parking System  —  " + parkingLot.getLotName());
        setSize(1050, 660);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // close DB connection cleanly when window is closed
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                parkingLot.saveSessionToDatabase();
                parkingLot.getDb().close();
                dispose();
                System.exit(0);
            }
        });

        buildUI(parkingLot);
    }

    private void buildUI(ParkingLot parkingLot) {
        setLayout(new BorderLayout());

        // ===================== LEFT SIDEBAR =====================
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // logo area at top
        sidebar.add(Box.createRigidArea(new Dimension(0, 35)));

        JLabel logo1 = makeLogoLabel("SMART");
        JLabel logo2 = makeLogoLabel("PARKING");
        JLabel logo3 = makeLogoLabel("SYSTEM");
        sidebar.add(logo1);
        sidebar.add(logo2);
        sidebar.add(logo3);

        sidebar.add(Box.createRigidArea(new Dimension(0, 25)));

        // horizontal divider line
        JPanel divider = new JPanel();
        divider.setBackground(new Color(55, 68, 90));
        divider.setMaximumSize(new Dimension(175, 1));
        divider.setAlignmentX(CENTER_ALIGNMENT);
        sidebar.add(divider);

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        // ===================== CONTENT PANELS =====================
        cardLayout    = new CardLayout();
        contentPanel  = new JPanel(cardLayout);
        contentPanel.setBackground(CONTENT_BG);

        dashboardPanel   = new DashboardPanel(parkingLot);
        ParkingPanel parkPanel   = new ParkingPanel(parkingLot, this);
        UnparkPanel  unparkPanel = new UnparkPanel(parkingLot, this);
        summaryPanel     = new SummaryPanel(parkingLot);
        lastSessionPanel = new LastSessionPanel(parkingLot);

        contentPanel.add(dashboardPanel,   "DASHBOARD");
        contentPanel.add(parkPanel,        "PARK");
        contentPanel.add(unparkPanel,      "UNPARK");
        contentPanel.add(summaryPanel,     "SUMMARY");
        contentPanel.add(lastSessionPanel, "LASTSESSION");

        // ===================== NAV BUTTONS =====================
        // label and card name paired together
        String[] labels = { "  Dashboard", "  Park Vehicle", "  Unpark / Checkout", "  Session Summary", "  Last Session" };
        String[] cards  = { "DASHBOARD",   "PARK",           "UNPARK",              "SUMMARY",           "LASTSESSION" };

        for (int i = 0; i < labels.length; i++) {
            JButton btn = makeNavButton(labels[i], cards[i]);
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 6)));
        }

        // pushes the footer to the bottom
        sidebar.add(Box.createVerticalGlue());

        JLabel footerLabel = new JLabel("  v1.0  Smart Parking");
        footerLabel.setFont(new Font("SansSerif", Font.ITALIC, 10));
        footerLabel.setForeground(new Color(90, 105, 130));
        footerLabel.setAlignmentX(LEFT_ALIGNMENT);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(0, 18, 18, 0));
        sidebar.add(footerLabel);

        add(sidebar,      BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // start on the dashboard screen
        cardLayout.show(contentPanel, "DASHBOARD");
    }

    private JLabel makeLogoLabel(String text) {
        JLabel lbl = new JLabel("  " + text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 21));
        lbl.setForeground(ACCENT_BLUE);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        return lbl;
    }

    private JButton makeNavButton(String label, String cardName) {
        JButton btn = new JButton(label);
        btn.setMaximumSize(new Dimension(200, 46));
        btn.setPreferredSize(new Dimension(200, 46));
        btn.setBackground(NAV_BTN_BG);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // simple hover effect - changes background color on mouse enter/exit
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(NAV_BTN_HOVER); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(NAV_BTN_BG); }
        });

        btn.addActionListener(e -> showPanel(cardName));

        return btn;
    }

    // called by ParkingPanel and UnparkPanel after an action finishes
    // also called internally when nav buttons are clicked
    public void showPanel(String cardName) {
        cardLayout.show(contentPanel, cardName);

        // refresh data when switching to these panels
        if (cardName.equals("DASHBOARD"))   dashboardPanel.refresh();
        if (cardName.equals("SUMMARY"))     summaryPanel.refresh();
        if (cardName.equals("LASTSESSION")) lastSessionPanel.refresh();
    }

    // called by park/unpark panels to refresh the map without switching away
    public void refreshDashboard() {
        dashboardPanel.refresh();
    }
}
