package efood.gui;

import efood.models.StoreOwner;
import efood.utils.DatabaseManager;
import efood.main.EfoodApp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class StoreManagementFrame extends JFrame {
    private JTextField titleField, priceField;
    private JCheckBox veganCheck, sponsorCheck; 
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private StoreOwner currentOwner;

    public StoreManagementFrame(StoreOwner owner) {
        this.currentOwner = owner;
        setTitle("Διαχείριση Καταστήματος: " + owner.getStoreName());
        setSize(850, 700); 
        
        // Το πρόγραμμα κρατάει το Session. Το session.txt σβήνει ΜΟΝΟ αν πατήσεις "Αποσύνδεση".
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // αν δεν τον έχει κάνει accept ο admin, του πετάμε μήνυμα και σταματάμε
        if (owner.getApprovalStatus() == StoreOwner.Status.PENDING) {
            showStatusMessage("Ο λογαριασμός σας εκκρεμεί έγκριση.<br>Θα μπορείτε να διαχειριστείτε το κατάστημα μόλις εγκριθείτε.", Color.ORANGE);
            return;
        } 
        else if (owner.getApprovalStatus() == StoreOwner.Status.REJECTED) {
            showStatusMessage("Η ΑΙΤΗΣΗ ΣΑΣ ΑΠΟΡΡΙΦΘΗΚΕ.<br>Το κατάστημά σας δεν πληροί τις προϋποθέσεις.", Color.RED);
            return;
        }

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10)); 
        inputPanel.setBorder(BorderFactory.createTitledBorder("Διαχείριση Μενού & Προφίλ Καταστήματος"));

        inputPanel.add(new JLabel("Όνομα Προϊόντος:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Τιμή (€):"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        veganCheck = new JCheckBox("Είναι Vegan;");
        inputPanel.add(veganCheck);

        sponsorCheck = new JCheckBox("Sponsor Store (Χορηγούμενο)");
        inputPanel.add(sponsorCheck);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // ΠΡΑΣΙΝΟ κουμπί αποθήκευσης (Καθαρό, χωρίς άχρηστες εντολές)
        JButton smartSaveBtn = new JButton("Αποθήκευση (Νέο / Αλλαγή)");
        smartSaveBtn.setBackground(new Color(150, 255, 150));
        
        JButton clearBtn = new JButton("Καθαρισμός Επιλογής");
        
        // ΚΟΚΚΙΝΟ κουμπί διαγραφής
        JButton deleteBtn = new JButton("Διαγραφή"); 
        deleteBtn.setBackground(new Color(255, 100, 100));
        deleteBtn.setForeground(Color.WHITE);
        
        buttonRow.add(clearBtn);
        buttonRow.add(smartSaveBtn);
        buttonRow.add(deleteBtn);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(inputPanel, BorderLayout.CENTER);
        northPanel.add(buttonRow, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        String[] cols = {"Όνομα", "Τιμή (€)", "Vegan"};
        // override για να μην μπορεί να κάνει edit κατευθείαν μέσα στα κελιά
        tableModel = new DefaultTableModel(null, cols) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        productsTable = new JTable(tableModel);
        add(new JScrollPane(productsTable), BorderLayout.CENTER);

        JButton logout = new JButton("Αποσύνδεση");
        logout.addActionListener(e -> { 
            EfoodApp.clearSession(); 
            dispose(); 
            new LoginFrame().setVisible(true); 
        });
        add(logout, BorderLayout.SOUTH);

        loadProducts();

        // listener: όταν πατάει σε μια γραμμή, τα πεδία γεμίζουν αυτόματα για να τα κάνει edit
        productsTable.getSelectionModel().addListSelectionListener(e -> {
            int row = productsTable.getSelectedRow();
            if (row != -1) {
                titleField.setText(tableModel.getValueAt(row, 0).toString());
                priceField.setText(tableModel.getValueAt(row, 1).toString());
                veganCheck.setSelected(tableModel.getValueAt(row, 2).toString().equals("Ναι"));
            }
        });

        smartSaveBtn.addActionListener(e -> {
            // Αφαιρούμε τυχόν κόμματα από τον τίτλο για να μην καταστραφεί η δομή του CSV αρχείου
            String title = titleField.getText().trim().replace(",", " ");
            // αλλάζουμε το κόμμα σε τελεία για να μη σκάσει η Double.parseDouble
            String priceStr = priceField.getText().trim().replace(",", ".");
            if (title.isEmpty() || priceStr.isEmpty()) return;
            
            try {
                double price = Double.parseDouble(priceStr);
                
                // βλέπουμε αν έχει επιλέξει γραμμή για να καταλάβουμε αν κάνει νέο ή update
                int row = productsTable.getSelectedRow();
                
                if (row == -1) {
                    if (DatabaseManager.saveProduct(currentOwner.getEmail(), title, price, veganCheck.isSelected())) {
                        loadProducts();
                        clearFields();
                        if (sponsorCheck.isSelected()) {
                            JOptionPane.showMessageDialog(this, "Το νέο προϊόν προστέθηκε.\nΤο κατάστημα σημειώθηκε ως Χορηγούμενο!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Το νέο προϊόν προστέθηκε!");
                        }
                    }
                } else {
                    // κρατάμε το παλιό όνομα για να το βρει στη βάση (csv) και να το κάνει update
                    String oldTitle = tableModel.getValueAt(row, 0).toString();
                    if (DatabaseManager.updateProduct(currentOwner.getEmail(), oldTitle, title, price, veganCheck.isSelected())) {
                        JOptionPane.showMessageDialog(this, "Το προϊόν ενημερώθηκε επιτυχώς!");
                        loadProducts();
                        clearFields();
                    }
                }
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Λάθος μορφή τιμής! Βάλτε αριθμό (π.χ. 2.50)"); 
            }
        });
        
        clearBtn.addActionListener(e -> {
            clearFields();
        });

        deleteBtn.addActionListener(e -> {
            int row = productsTable.getSelectedRow();
            // τσεκάρουμε αν πάτησε διαγραφή χωρίς να έχει επιλέξει κάτι
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Επιλέξτε προϊόν προς διαγραφή από τον πίνακα!");
                return;
            }
            String oldTitle = tableModel.getValueAt(row, 0).toString();
            if (DatabaseManager.deleteProduct(currentOwner.getEmail(), oldTitle)) {
                JOptionPane.showMessageDialog(this, "Το προϊόν διαγράφηκε επιτυχώς!");
                loadProducts();
                clearFields();
            }
        });
    }

    private void showStatusMessage(String text, Color color) {
        JPanel msgPanel = new JPanel(new GridBagLayout());
        JLabel msg = new JLabel("<html><center>" + text + "</center></html>", SwingConstants.CENTER);
        msg.setFont(new Font("Arial", Font.BOLD, 18));
        msg.setForeground(color);
        msgPanel.add(msg);
        add(msgPanel, BorderLayout.CENTER);
        
        JButton back = new JButton("Επιστροφή");
        back.addActionListener(e -> { 
            EfoodApp.clearSession();
            dispose(); 
            new LoginFrame().setVisible(true); 
        });
        add(back, BorderLayout.SOUTH);
    }

    private void clearFields() {
        titleField.setText(""); 
        priceField.setText("");
        veganCheck.setSelected(false); 
        productsTable.clearSelection(); 
    }

    private void loadProducts() {
        tableModel.setRowCount(0);
        // διαβάζει τα products και δείχνει μόνο όσα έχουν το email αυτού του μαγαζιού
        try (Scanner sc = new Scanner(new File("data/products.csv"))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                if (p.length >= 4 && p[0].equals(currentOwner.getEmail())) {
                    tableModel.addRow(new Object[]{ p[1], p[2], p[3].equals("true") ? "Ναι" : "Όχι" });
                }
            }
        } catch (Exception e) {}
    }
}