package efood.gui;

import efood.models.*;
import efood.utils.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class AdminDashboardFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> filterCombo;

    public AdminDashboardFrame() {
        setTitle("Πίνακας Ελέγχου Διαχειριστή (Admin)");
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Πάνω μέρος: Τίτλος και Φίλτρο Κατηγοριών ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Διαχείριση Χρηστών & Αιτήσεων Συνεργατών", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterPanel.add(new JLabel("Κατηγορία Προβολής: "));
        
        // Καθαρές κατηγορίες που περιλαμβάνουν και τα καταστήματα
        String[] filters = {
            "Εκκρεμείς Αιτήσεις (Διανομείς & Καταστήματα)", 
            "Εγκεκριμένα Καταστήματα", 
            "Ιστορικό Όλων των Χρηστών"
        };
        filterCombo = new JComboBox<>(filters);
        filterCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        filterCombo.addActionListener(e -> loadData()); 
        
        filterPanel.add(filterCombo);
        topPanel.add(filterPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // --- Πίνακας ---
        String[] cols = {"Ονοματεπώνυμο", "Email", "Τηλέφωνο", "Πληροφορίες (Όχημα/Κατάστημα)", "Κατάσταση"};
        model = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Κουμπιά Ενεργειών ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        
        JButton viewImgBtn = new JButton("📷 Δίπλωμα");
        viewImgBtn.setBackground(new Color(100, 200, 255));
        
        JButton viewCvBtn = new JButton("📄 Βιογραφικό (PDF)");
        viewCvBtn.setBackground(new Color(255, 200, 100));

        JButton approveBtn = new JButton("✔ Έγκριση");
        approveBtn.setBackground(new Color(100, 255, 100));
        
        JButton rejectBtn = new JButton("✖ Απόρριψη");
        rejectBtn.setBackground(new Color(255, 100, 100));
        rejectBtn.setForeground(Color.WHITE);

        JButton logoutBtn = new JButton("Αποσύνδεση");

        // Σύνδεση Κουμπιών με Μεθόδους
        viewCvBtn.addActionListener(e -> viewCV());
        viewImgBtn.addActionListener(e -> viewImage());
        
        // Η έγκριση/απόρριψη τώρα δουλεύει για όλους τους συνεργάτες
        approveBtn.addActionListener(e -> updateStatus("APPROVED"));
        rejectBtn.addActionListener(e -> updateStatus("REJECTED"));
        
        logoutBtn.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });

        bottomPanel.add(viewImgBtn);
        bottomPanel.add(viewCvBtn);
        bottomPanel.add(approveBtn);
        bottomPanel.add(rejectBtn);
        bottomPanel.add(logoutBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadData(); 
    }

    private void loadData() {
        model.setRowCount(0);
        HashMap<String, User> users = DatabaseManager.loadUsers();
        int selection = filterCombo.getSelectedIndex();

        for (User u : users.values()) {
            if (selection == 0) { // Μόνο Εκκρεμείς (Διανομείς ΚΑΙ Καταστήματα)
                if (u instanceof DeliveryDriver && ((DeliveryDriver) u).getApprovalStatus() == DeliveryDriver.Status.PENDING) {
                    addDriverToTable((DeliveryDriver) u);
                } else if (u instanceof StoreOwner && ((StoreOwner) u).getApprovalStatus() == StoreOwner.Status.PENDING) {
                    addOwnerToTable((StoreOwner) u);
                }
            } else if (selection == 1) { // Μόνο Εγκεκριμένα Καταστήματα
                if (u instanceof StoreOwner && ((StoreOwner) u).getApprovalStatus() == StoreOwner.Status.APPROVED) {
                    addOwnerToTable((StoreOwner) u);
                }
            } else if (selection == 2) { // Όλοι οι Χρήστες (Ιστορικό)
                if (u instanceof DeliveryDriver) addDriverToTable((DeliveryDriver) u);
                else if (u instanceof StoreOwner) addOwnerToTable((StoreOwner) u);
                else if (u instanceof Customer) {
                    model.addRow(new Object[]{ u.getFullName(), u.getEmail(), u.getPhoneNumber(), "Πελάτης", "Ενεργός" });
                }
            }
        }
    }

    private void addDriverToTable(DeliveryDriver d) {
        String statusStr = (d.getApprovalStatus() == DeliveryDriver.Status.PENDING) ? "Σε Εκκρεμότητα (Διανομέας)" : 
                          (d.getApprovalStatus() == DeliveryDriver.Status.APPROVED) ? "Εγκεκριμένος" : "Απορριφθείς";
        model.addRow(new Object[]{ d.getFullName(), d.getEmail(), d.getPhoneNumber(), "Όχημα: " + d.getVehicleLicense(), statusStr });
    }

    private void addOwnerToTable(StoreOwner o) {
        String statusStr = (o.getApprovalStatus() == StoreOwner.Status.PENDING) ? "Σε Εκκρεμότητα (Κατάστημα)" : 
                          (o.getApprovalStatus() == StoreOwner.Status.APPROVED) ? "Εγκεκριμένος Συνεργάτης" : "Απορριφθείς";
        model.addRow(new Object[]{ o.getFullName(), o.getEmail(), o.getPhoneNumber(), "Κατάστημα: " + o.getStoreName(), statusStr });
    }

    private void viewImage() {
        User u = getSelectedUser();
        if (u == null) return;
        
        if (!(u instanceof DeliveryDriver)) {
            JOptionPane.showMessageDialog(this, "Επιλέξτε έναν Διανομέα για να δείτε το δίπλωμα!", "Αδύνατη Ενέργεια", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DeliveryDriver d = (DeliveryDriver) u;
        String path = d.getImagePath();
        if (path == null || path.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Δεν έχει ανέβει φωτογραφία.");
            return;
        }

        File imgFile = new File(path);
        if (!imgFile.exists()) {
            JOptionPane.showMessageDialog(this, "Το αρχείο εικόνας δεν βρέθηκε.");
            return;
        }
        try {
            BufferedImage img = ImageIO.read(imgFile);
            double scale = Math.min(600.0 / img.getWidth(), 500.0 / img.getHeight());
            Image scaled = img.getScaledInstance((int)(img.getWidth()*scale), (int)(img.getHeight()*scale), Image.SCALE_SMOOTH);
            JOptionPane.showMessageDialog(this, new JLabel(new ImageIcon(scaled)), "Προβολή Διπλώματος", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την προβολή.");
        }
    }

    private void viewCV() {
        User u = getSelectedUser();
        if (u == null) return;

        if (!(u instanceof DeliveryDriver)) {
            JOptionPane.showMessageDialog(this, "Επιλέξτε έναν Διανομέα για να δείτε το βιογραφικό του!", "Αδύνατη Ενέργεια", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DeliveryDriver d = (DeliveryDriver) u;
        String path = d.getCvPath();
        if (path == null || path.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Δεν έχει ανέβει βιογραφικό.");
            return;
        }

        try {
            File pdf = new File(path);
            if (pdf.exists()) {
                Desktop.getDesktop().open(pdf);
            } else {
                JOptionPane.showMessageDialog(this, "Το αρχείο PDF δεν βρέθηκε.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά το άνοιγμα του PDF.");
        }
    }

    private void updateStatus(String statusStr) {
        User u = getSelectedUser();
        if (u == null) return;

        if (u instanceof Customer) {
            JOptionPane.showMessageDialog(this, "Οι πελάτες δεν χρειάζονται έγκριση.", "Προσοχή", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Χρησιμοποιούμε τη νέα μέθοδο του DatabaseManager που ενημερώνει όποιον συνεργάτη επιλέξουμε
        if (DatabaseManager.updateUserStatus(u.getEmail(), statusStr)) {
            JOptionPane.showMessageDialog(this, "Η κατάσταση του χρήστη " + u.getFullName() + " ενημερώθηκε!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Αποτυχία ενημέρωσης στο αρχείο.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    private User getSelectedUser() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε έναν χρήστη από τον πίνακα.");
            return null;
        }
        String email = (String) model.getValueAt(row, 1);
        return DatabaseManager.loadUsers().get(email);
    }
}