package efood.gui;

import efood.models.DeliveryDriver;
import efood.utils.DatabaseManager;
import efood.main.EfoodApp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DelivererDashboardFrame extends JFrame {

    private boolean hasActiveOrder = false; 
    private String activeOrderId = ""; 

    public DelivererDashboardFrame(DeliveryDriver currentDriver) {
        setTitle("Dashboard Διανομέα");
        setSize(950, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        mainPanel.setBackground(new Color(248, 248, 248));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JPanel infoPanel = new JPanel(new GridLayout(2,1));
        infoPanel.setOpaque(false);
        JLabel nameLabel = new JLabel("Διανομέας: " + currentDriver.getFullName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        
        String statusText;
        if (currentDriver.getApprovalStatus() == DeliveryDriver.Status.PENDING) statusText = "Σε αναμονή (Pending)";
        else if (currentDriver.getApprovalStatus() == DeliveryDriver.Status.REJECTED) statusText = "Απορρίφθηκε (Rejected)";
        else statusText = "Ενεργός";
        
        JLabel licenseLabel = new JLabel("Αρ. Αδείας: " + currentDriver.getVehicleLicense() + " | Κατάσταση: " + statusText);
        licenseLabel.setForeground(Color.DARK_GRAY);
        
        infoPanel.add(nameLabel);
        infoPanel.add(licenseLabel);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        
        JButton refreshBtn = new JButton("🔄 Ανανέωση");
        
        JButton deleteBtn = new JButton("Διαγραφή");
        deleteBtn.setBackground(new Color(200, 0, 0));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Είστε σίγουρος ότι θέλετε να διαγράψετε το λογαριασμό σας;", "Προσοχή", JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION) {
                if(DatabaseManager.deleteUser(currentDriver.getEmail())) {
                    JOptionPane.showMessageDialog(this, "Ο λογαριασμός διεγράφη επιτυχώς.");
                    EfoodApp.clearSession();
                    dispose();
                    new LoginFrame().setVisible(true);
                }
            }
        });
        
        JButton logoutBtn = new JButton("Αποσύνδεση");
        logoutBtn.addActionListener(e -> { 
            EfoodApp.clearSession(); 
            dispose(); 
            new LoginFrame().setVisible(true); 
        });

        btnPanel.add(refreshBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(logoutBtn);
        
        headerPanel.add(infoPanel, BorderLayout.WEST);
        headerPanel.add(btnPanel, BorderLayout.EAST);
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        if (currentDriver.getApprovalStatus() == DeliveryDriver.Status.PENDING) {
            JTextArea pendingMsg = new JTextArea("Ο λογαριασμός σας βρίσκεται υπό εξέταση.\n\nΌταν εγκριθεί από τον διαχειριστή, θα εμφανιστούν εδώ οι\nδιαθέσιμες παραγγελίες προς παράδοση.");
            pendingMsg.setFont(new Font("Arial", Font.BOLD, 16));
            pendingMsg.setEditable(false);
            pendingMsg.setOpaque(false);
            mainPanel.add(pendingMsg);
            mainPanel.add(Box.createVerticalGlue());
        } 
        else if (currentDriver.getApprovalStatus() == DeliveryDriver.Status.REJECTED) {
            JTextArea rejectMsg = new JTextArea("Η ΑΙΤΗΣΗ ΣΑΣ ΑΠΟΡΡΙΦΘΗΚΕ.\n\nΤα στοιχεία ή το δίπλωμα που υποβάλατε δεν πληρούν\nτις προϋποθέσεις του eFood. Ο λογαριασμός έχει κλειδωθεί.");
            rejectMsg.setFont(new Font("Arial", Font.BOLD, 16));
            rejectMsg.setForeground(Color.RED);
            rejectMsg.setEditable(false);
            rejectMsg.setOpaque(false);
            mainPanel.add(rejectMsg);
            mainPanel.add(Box.createVerticalGlue());
        }
        else {
            JLabel tableTitle = new JLabel("Διαθέσιμες Παραγγελίες προς Παράδοση:");
            tableTitle.setFont(new Font("Arial", Font.BOLD, 13));
            tableTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(tableTitle);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            String[] columnNames = {"ID", "Κατάστημα", "Διεύθυνση", "Αμοιβή"};
            DefaultTableModel model = new DefaultTableModel(null, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) { return false; }
            };
            JTable ordersTable = new JTable(model);
            ordersTable.setRowHeight(30);
            
            loadOrdersIntoTable(model);
            refreshBtn.addActionListener(e -> loadOrdersIntoTable(model));

            JScrollPane scrollPane = new JScrollPane(ordersTable);
            scrollPane.setPreferredSize(new Dimension(750, 180)); 
            scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(scrollPane);
            
            mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            
            JButton acceptBtn = new JButton("ΑΠΟΔΟΧΗ ΕΠΙΛΕΓΜΕΝΗΣ ΠΑΡΑΓΓΕΛΙΑΣ");
            acceptBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(acceptBtn);

            mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

            JLabel currentOrderTitle = new JLabel("Στοιχεία Τρέχουσας Παραγγελίας:");
            currentOrderTitle.setFont(new Font("Arial", Font.BOLD, 13));
            currentOrderTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(currentOrderTitle);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            JPanel currentOrderPanel = new JPanel();
            currentOrderPanel.setLayout(new BoxLayout(currentOrderPanel, BoxLayout.Y_AXIS));
            currentOrderPanel.setBackground(new Color(255, 252, 220)); 
            currentOrderPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 180, 0), 1),
                    BorderFactory.createEmptyBorder(15, 25, 15, 25)));
            currentOrderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
            currentOrderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel pickupLbl = new JLabel("Αναμονή για αποδοχή παραγγελίας...");
            JLabel dropoffLbl = new JLabel("");
            JLabel phoneLbl = new JLabel("");
            currentOrderPanel.add(pickupLbl);
            currentOrderPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            currentOrderPanel.add(dropoffLbl);
            currentOrderPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            currentOrderPanel.add(phoneLbl);
            
            mainPanel.add(currentOrderPanel);
            mainPanel.add(Box.createVerticalGlue());

            JButton deliveredBtn = new JButton("ΣΗΜΑΝΣΗ ΩΣ ΠΑΡΑΔΟΘΗΚΕ");
            deliveredBtn.setBackground(new Color(255, 175, 175));
            deliveredBtn.setPreferredSize(new Dimension(950, 60)); 
            deliveredBtn.setFont(new Font("Arial", Font.BOLD, 15));
            deliveredBtn.setEnabled(false); 
            add(deliveredBtn, BorderLayout.SOUTH);
            
            acceptBtn.addActionListener(e -> {
                if(hasActiveOrder) {
                    JOptionPane.showMessageDialog(this, "Έχετε ήδη αναλάβει μια παραγγελία! Παραδώστε την πρώτα.");
                    return;
                }
                int row = ordersTable.getSelectedRow();
                if(row == -1) {
                    JOptionPane.showMessageDialog(this, "Παρακαλώ επιλέξτε μια παραγγελία από τον πίνακα.");
                    return;
                }
                
                String id = (String) model.getValueAt(row, 0);
                String store = (String) model.getValueAt(row, 1);
                String addr = (String) model.getValueAt(row, 2);
                
                activeOrderId = id; 
                pickupLbl.setText("📍 Παραλαβή: " + store + " (" + id + ")");
                dropoffLbl.setText("🏠 Παράδοση: " + addr);
                phoneLbl.setText("📞 Τηλέφωνο Πελάτη: 69" + (int)(Math.random()*100000000));
                
                hasActiveOrder = true;
                ordersTable.setEnabled(false); 
                deliveredBtn.setEnabled(true); 
                deliveredBtn.setBackground(new Color(100, 255, 100)); 
            });
            
            deliveredBtn.addActionListener(e -> {
                DatabaseManager.completeOrder(activeOrderId); 
                loadOrdersIntoTable(model); 
                
                hasActiveOrder = false;
                activeOrderId = "";
                ordersTable.setEnabled(true);
                pickupLbl.setText("Αναμονή για αποδοχή παραγγελίας...");
                dropoffLbl.setText("");
                phoneLbl.setText("");
                
                deliveredBtn.setEnabled(false);
                deliveredBtn.setBackground(new Color(255, 175, 175));
                JOptionPane.showMessageDialog(this, "Συγχαρητήρια! Η παραγγελία παραδόθηκε επιτυχώς.");
            });
        }

        add(mainPanel, BorderLayout.CENTER);
    }

    private void loadOrdersIntoTable(DefaultTableModel model) {
        model.setRowCount(0);
        ArrayList<String[]> pending = DatabaseManager.loadPendingOrders();
        for (String[] order : pending) {
            model.addRow(order);
        }
    }
}