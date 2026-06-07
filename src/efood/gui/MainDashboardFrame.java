package efood.gui;

import efood.models.Customer;
import efood.models.StoreOwner;
import efood.models.User;
import efood.utils.DatabaseManager;
import efood.main.EfoodApp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class MainDashboardFrame extends JFrame {

    private Customer currentCustomer;
    private ArrayList<String[]> myCart; 
    private JTable storesTable;
    private DefaultTableModel storesModel;
    private Timer pinataTimer;

    public static long pinataExpireTime = 0;
    public static String activePinataCode = "";
    public static double activePinataDiscount = 0.0;
    
    private final String[] promoCodes = {"LUCKY10", "MEGA15", "SUPER20", "BOMB25", "CRAZY30", "EFOOD5", "TREAT12", "GIFT18", "MAGIC22", "EPIC50"};
    private final double[] promoDiscounts = {0.10, 0.15, 0.20, 0.25, 0.30, 0.05, 0.12, 0.18, 0.22, 0.50};

    public MainDashboardFrame(Customer currentCustomer) {
        this(currentCustomer, new ArrayList<>()); 
    }

    public MainDashboardFrame(Customer currentCustomer, ArrayList<String[]> existingCart) {
        this.currentCustomer = currentCustomer;
        this.myCart = existingCart; 
        
        setTitle("eFood - Αρχική Πελάτη");
        setSize(900, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel = new JLabel("Καλωσήρθες, " + currentCustomer.getFullName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel pointsLabel = new JLabel("Πόντοι: " + currentCustomer.getLoyaltyPoints() + " 🎁");
        pointsLabel.setForeground(new Color(50, 150, 50));
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        topPanel.add(welcomeLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(pointsLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 20))); 

        JPanel pinataPanel = new JPanel(null); 
        pinataPanel.setPreferredSize(new Dimension(850, 40));
        pinataPanel.setMaximumSize(new Dimension(850, 40));
        pinataPanel.setOpaque(false);
        
        JButton pinataBtn = new JButton("🪅 Lucky Pinata!");
        pinataBtn.setFont(new Font("Arial", Font.BOLD, 16));
        pinataBtn.setForeground(new Color(150, 50, 200));
        pinataBtn.setBounds(10, 5, 160, 30);
        pinataBtn.setContentAreaFilled(false); 
        pinataBtn.setBorderPainted(false);
        pinataBtn.setFocusPainted(false);
        pinataBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        
        pinataBtn.addActionListener(e -> {
            pinataTimer.stop(); 
            showLuckyOfferDialog(); 
        });

        pinataPanel.add(pinataBtn);
        
        pinataTimer = new Timer(30, new ActionListener() {
            int x = 10;
            int dx = 4;
            @Override
            public void actionPerformed(ActionEvent e) {
                x += dx;
                if (x > 700 || x < 10) dx = -dx; 
                pinataBtn.setLocation(x, 5); 
            }
        });
        pinataTimer.start();
        topPanel.add(pinataPanel);

        add(topPanel, BorderLayout.NORTH);

        String[] storeCols = {"Όνομα Καταστήματος", "Email"};
        // override για να κλειδώσουμε τα κελιά του πίνακα, να μην μπορεί ο χρήστης να γράψει μέσα
        storesModel = new DefaultTableModel(null, storeCols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        storesTable = new JTable(storesModel);
        storesTable.setRowHeight(40);
        storesTable.setFont(new Font("Arial", Font.BOLD, 14));
        loadStores();

        JScrollPane scrollPane = new JScrollPane(storesTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Επίλεξε Κατάστημα για να δεις το Μενού"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        
        JButton openStoreBtn = new JButton("📖 Άνοιγμα Καταλόγου");
        openStoreBtn.setBackground(new Color(150, 200, 255));
        openStoreBtn.addActionListener(e -> {
            int row = storesTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Επίλεξε ένα μαγαζί από τον πίνακα!");
                return;
            }
            String storeName = (String) storesModel.getValueAt(row, 0);
            String storeEmail = (String) storesModel.getValueAt(row, 1);
            dispose();
            new StoreCatalogFrame(currentCustomer, storeName, storeEmail, myCart).setVisible(true);
        });

        double total = 0;
        for (String[] item : myCart) total += Double.parseDouble(item[1].replace(",", "."));
        
        JButton cartBtn = new JButton("🛒 Καλάθι (" + myCart.size() + " | " + String.format(java.util.Locale.US, "%.2f", total) + "€)");
        cartBtn.setBackground(new Color(100, 200, 100));
        cartBtn.addActionListener(e -> {
            if (myCart.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Το καλάθι σου είναι άδειο!");
                return;
            }
            dispose();
            new CartCheckoutFrame(currentCustomer, myCart).setVisible(true);
        });

        JButton activeOrderBtn = new JButton("📦 Ενεργή Παραγγελία");
        activeOrderBtn.setBackground(new Color(255, 200, 100));
        activeOrderBtn.addActionListener(e -> {
            ArrayList<String> history = currentCustomer.getOrderHistory();
            if (history.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν έχετε καμία παραγγελία στο ιστορικό σας.\nΔεν υπάρχει ενεργή παραγγελία.");
            } else {
                String lastOrder = history.get(history.size() - 1);
                String orderId = "";
                
                if (lastOrder.startsWith("ID: ")) {
                    orderId = lastOrder.substring(4, lastOrder.indexOf("\n"));
                }
                
                if (!orderId.isEmpty()) {
                    String status = DatabaseManager.getOrderStatus(orderId);
                    if (status.equals("PENDING")) {
                        JOptionPane.showMessageDialog(this, "Η παραγγελία σας (" + orderId + ") είναι ΚΑΘ' ΟΔΟΝ 🛵\n\n" + lastOrder);
                    } else if (status.equals("COMPLETED")) {
                        JOptionPane.showMessageDialog(this, "Η προηγούμενη παραγγελία με αριθμό " + orderId + " ΠΑΡΑΔΟΘΗΚΕ ✔️\nΔεν έχετε ενεργή παραγγελία αυτή τη στιγμή.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Δεν βρέθηκε ενεργή παραγγελία.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Τελευταία Παραγγελία:\n" + lastOrder);
                }
            }
        });

        JButton settingsBtn = new JButton("⚙ Ρυθμίσεις");
        settingsBtn.addActionListener(e -> { dispose(); new ProfileSettingFrame(currentCustomer, myCart).setVisible(true); });

        JButton logoutBtn = new JButton("Αποσύνδεση");
        logoutBtn.addActionListener(e -> { 
            EfoodApp.clearSession();
            dispose(); 
            new LoginFrame().setVisible(true); 
        });

        bottomPanel.add(openStoreBtn);
        bottomPanel.add(cartBtn);
        bottomPanel.add(activeOrderBtn);
        bottomPanel.add(settingsBtn);
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadStores() {
        HashMap<String, User> users = DatabaseManager.loadUsers();
        for (User u : users.values()) {
            // φέρνει μόνο τα μαγαζιά που έχουν πάρει έγκριση (APPROVED) από τον admin
            if (u instanceof StoreOwner && ((StoreOwner) u).getApprovalStatus() == StoreOwner.Status.APPROVED) {
                storesModel.addRow(new Object[]{((StoreOwner) u).getStoreName(), u.getEmail()});
            }
        }
    }

    private void showLuckyOfferDialog() {
        JDialog dialog = new JDialog(this, "Ειδική Προσφορά!", true);
        dialog.setSize(450, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel msg = new JLabel("Συγχαρητήρια! Βρήκες την Πινιάτα!");
        msg.setFont(new Font("Arial", Font.BOLD, 18));
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        long currentTime = System.currentTimeMillis();
        
        if (pinataExpireTime == 0 || currentTime > pinataExpireTime) {
            int randIndex = (int) (Math.random() * promoCodes.length);
            activePinataCode = promoCodes[randIndex];
            activePinataDiscount = promoDiscounts[randIndex];
            pinataExpireTime = currentTime + (20 * 60 * 1000); 
        }
        
        int discountPercentage = (int) (activePinataDiscount * 100);
        
        JLabel codeMsg = new JLabel("Κωδικός: " + activePinataCode + " (-" + discountPercentage + "%)");
        codeMsg.setFont(new Font("Arial", Font.BOLD, 18));
        codeMsg.setForeground(new Color(200, 50, 50));
        codeMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel timeLabel = new JLabel("Υπολογισμός χρόνου...");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        p.add(msg);
        p.add(Box.createRigidArea(new Dimension(0, 15)));
        p.add(codeMsg);
        p.add(Box.createRigidArea(new Dimension(0, 15)));
        p.add(timeLabel);
        
        dialog.add(p, BorderLayout.CENTER);
        
        Timer countdown = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long remainingMillis = pinataExpireTime - System.currentTimeMillis();
                
                if (remainingMillis <= 0) {
                    ((Timer)e.getSource()).stop();
                    pinataExpireTime = 0; 
                    dialog.dispose();
                    JOptionPane.showMessageDialog(MainDashboardFrame.this, "Ο χρόνος της προσφοράς έληξε!");
                } else {
                    int secondsLeft = (int) (remainingMillis / 1000);
                    int m = secondsLeft / 60;
                    int s = secondsLeft % 60;
                    timeLabel.setText(String.format("Έχεις %02d:%02d λεπτά για να τον χρησιμοποιήσεις!", m, s));
                }
            }
        });
        countdown.start();
        
        JButton okBtn = new JButton("Τέλεια, Ευχαριστώ!");
        okBtn.addActionListener(e -> dialog.dispose());
        dialog.add(okBtn, BorderLayout.SOUTH);
        
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                // όταν κλείσει το παραθυράκι, η πινιάτα ξεκινάει πάλι να κουνιέται από εκεί που σταμάτησε
                pinataTimer.start(); 
            }
        });
        
        dialog.setVisible(true);
    }
}