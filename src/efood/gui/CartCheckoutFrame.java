package efood.gui;

import efood.models.Customer;
import efood.utils.DatabaseManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class CartCheckoutFrame extends JFrame {

    private Customer currentCustomer;
    private ArrayList<String[]> myCart; 
    private double totalAmount = 0.0;
    private double discountRate = 0.0;
    private double loyaltyDiscount = 0.0; 

    private JLabel finalTotal;
    private JButton finishBtn;
    private JCheckBox loyaltyCheck; 
    
    private HashSet<String> usedPromoCodes = new HashSet<>();

    public CartCheckoutFrame(Customer customer, ArrayList<String[]> cart) {
        this.currentCustomer = customer;
        this.myCart = cart;
        
        setTitle("Ταμείο");
        setSize(900, 850); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 100, 20, 100));

        JLabel title = new JLabel("Ολοκλήρωση Παραγγελίας");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        String[] cols = {"Προϊόν", "Τιμή"};
        // override για να μην μπορεί να γράψει μέσα στον πίνακα
        DefaultTableModel model = new DefaultTableModel(null, cols) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        for (String[] item : cart) {
            model.addRow(new Object[]{item[0], item[1] + "€"});
            // αλλάζουμε τυχόν κόμματα σε τελείες γιατί αλλιώς η parseDouble θα πετάξει Exception
            totalAmount += Double.parseDouble(item[1].replace(",", "."));
        }
        JTable table = new JTable(model);
        table.setRowHeight(30);
        mainPanel.add(new JScrollPane(table));
        
        JButton removeBtn = new JButton("🗑️ Διαγραφή Επιλεγμένου");
        removeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBtn.setBackground(new Color(255, 100, 100));
        removeBtn.setForeground(Color.WHITE);
        // Ενέργεια διαγραφής επιλεγμένου προϊόντος από το καλάθι
        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Επίλεξε ένα προϊόν από τον πίνακα.");
                return;
            }
            
            // Αφαιρούμε απευθείας βάσει index για να αποφύγουμε bugs με προϊόντα που έχουν το ίδιο όνομα
            totalAmount -= Double.parseDouble(myCart.get(row)[1].replace(",", ".")); 
            if (totalAmount < 0.001) totalAmount = 0.0; // αποφυγή αρνητικού συνόλου λόγω double precision
            
            myCart.remove(row);
            
            model.removeRow(row); 
            updateTotals(); 
        });
        // υπολογισμος εκπτωσης με loyalty points και promo codes
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(removeBtn);

        finalTotal = new JLabel("Σύνολο: " + String.format(java.util.Locale.US, "%.2f", totalAmount) + "€");
        finalTotal.setFont(new Font("Arial", Font.BOLD, 22));
        finalTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(finalTotal);

        loyaltyCheck = new JCheckBox("Εξαργύρωση " + currentCustomer.getLoyaltyPoints() + " Πόντων Loyalty");
        loyaltyCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
        loyaltyCheck.setFont(new Font("Arial", Font.BOLD, 14));
        loyaltyCheck.setForeground(new Color(50, 150, 50));
        
        loyaltyCheck.addActionListener(e -> {
            if (loyaltyCheck.isSelected()) {
                if (currentCustomer.getLoyaltyPoints() > 0) {
                    loyaltyDiscount = currentCustomer.getLoyaltyPoints() * 0.01;
                    updateTotals();
                    JOptionPane.showMessageDialog(this, "Κέρδισες " + String.format(java.util.Locale.US, "%.2f", loyaltyDiscount) + "€ έκπτωση από τους πόντους σου!");
                } else {
                    loyaltyCheck.setSelected(false);
                    JOptionPane.showMessageDialog(this, "Δεν έχεις αρκετούς πόντους Loyalty.");
                }
            } else {
                loyaltyDiscount = 0.0;
                updateTotals(); 
            }
        });
        mainPanel.add(loyaltyCheck);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel promoPanel = new JPanel(new FlowLayout());
        JTextField promoField = new JTextField("Promo Code...");
        promoField.setPreferredSize(new Dimension(200, 35));
        promoField.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { if(promoField.getText().equals("Promo Code...")) promoField.setText(""); }
            @Override public void focusLost(FocusEvent e) { if(promoField.getText().isEmpty()) promoField.setText("Promo Code..."); }
        });
        
        JButton applyBtn = new JButton("Εφαρμογή");
        applyBtn.addActionListener(e -> {
            String code = promoField.getText().trim().toUpperCase();
            
            if (code.equals("HMMY20") || code.equals("ΗΜΜΥ20")) {
                if (usedPromoCodes.contains(code)) {
                    JOptionPane.showMessageDialog(this, "Έχεις ήδη χρησιμοποιήσει αυτόν τον κωδικό!", "Προσοχή", JOptionPane.WARNING_MESSAGE);
                } else {
                    // βάζουμε τον κωδικό στο HashSet για να θυμόμαστε ότι τον χρησιμοποίησε ήδη
                    usedPromoCodes.add(code); 
                    discountRate = 0.20;
                    updateTotals(); 
                    JOptionPane.showMessageDialog(this, "Έκπτωση 20% εφαρμόστηκε!");
                }
            } 
            else if (!MainDashboardFrame.activePinataCode.isEmpty() && code.equals(MainDashboardFrame.activePinataCode)) {
                if (System.currentTimeMillis() > MainDashboardFrame.pinataExpireTime) {
                    JOptionPane.showMessageDialog(this, "Ο κωδικός έχει λήξει! Πάτα πάλι την πινιάτα για νέο.", "Προσοχή", JOptionPane.WARNING_MESSAGE);
                } else if (usedPromoCodes.contains(code)) {
                    JOptionPane.showMessageDialog(this, "Έχεις ήδη χρησιμοποιήσει αυτόν τον κωδικό!", "Προσοχή", JOptionPane.WARNING_MESSAGE);
                } else {
                    // το ίδιο και εδώ για τον κωδικό της πινιάτας
                    usedPromoCodes.add(code); 
                    discountRate = MainDashboardFrame.activePinataDiscount; 
                    updateTotals(); 
                    JOptionPane.showMessageDialog(this, "Έκπτωση " + (int)(MainDashboardFrame.activePinataDiscount * 100) + "% εφαρμόστηκε!");
                }
            } 
            else {
                JOptionPane.showMessageDialog(this, "Άκυρος Κωδικός.", "Προσοχή", JOptionPane.WARNING_MESSAGE);
            }
        });

        promoPanel.add(promoField);
        promoPanel.add(applyBtn);
        mainPanel.add(promoPanel);

        JPanel paymentRow = new JPanel(new FlowLayout());
        paymentRow.add(new JLabel("Τρόπος Πληρωμής:"));
        ArrayList<String> methods = new ArrayList<>();
        String defaultCard = null;
        for (String card : currentCustomer.getSavedCards()) {
            methods.add(card);
            if (card.contains("(Προεπιλογή)")) defaultCard = card;
        }
        methods.add("Μετρητά (Αντικαταβολή)");
        
        JComboBox<String> paymentCombo = new JComboBox<>(methods.toArray(new String[0]));
        if (defaultCard != null) paymentCombo.setSelectedItem(defaultCard);
        else paymentCombo.setSelectedItem("Μετρητά (Αντικαταβολή)");
        paymentRow.add(paymentCombo);
        mainPanel.add(paymentRow);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        finishBtn = new JButton("ΟΛΟΚΛΗΡΩΣΗ ΠΑΡΑΓΓΕΛΙΑΣ (" + String.format(java.util.Locale.US, "%.2f", totalAmount) + "€)");
        finishBtn.setBackground(new Color(150, 255, 150));
        finishBtn.setFont(new Font("Arial", Font.BOLD, 18));
        finishBtn.setPreferredSize(new Dimension(0, 60));
        
        finishBtn.addActionListener(e -> {
            if (myCart.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Το καλάθι είναι άδειο!");
                return;
            }
            
            finishBtn.setEnabled(false); 
            finishBtn.setText("Επεξεργασία πληρωμής... ⏳");
            finishBtn.setBackground(new Color(200, 200, 200));

            // Φτιάχνουμε ένα νέο Thread (νήμα) για να βάλουμε ένα μικρό delay (sleep) 2 δευτερολέπτων
            // Αυτό δίνει την αίσθηση ότι "φορτώνει" η τράπεζα, χωρίς όμως να παγώσει όλο το UI
            Thread paymentThread = new Thread(() -> {
                try {
                    Thread.sleep(2000); 
                } catch (InterruptedException ex) {}

                // το invokeLater εξασφαλίζει ότι το UI θα ενημερωθεί σωστά αφού τελειώσει η δουλειά στο background thread
                SwingUtilities.invokeLater(() -> {
                    double discountAmount = totalAmount * discountRate;
                    double finalPay = totalAmount - discountAmount - loyaltyDiscount;
                    // έλεγχος για να μην βγει αρνητικό το σύνολο μετά τις εκπτώσεις
                    if (finalPay < 0) finalPay = 0; 
                    
                    String orderId = "#" + (1000 + (int)(Math.random() * 9000));
                    String details = "ID: " + orderId + "\nΣύνολο: " + String.format(java.util.Locale.US, "%.2f", finalPay) + "€\nΤρόπος: " + paymentCombo.getSelectedItem();
                    currentCustomer.getOrderHistory().add(details);
                    
                    if (loyaltyCheck.isSelected()) {
                        currentCustomer.setLoyaltyPoints(0); 
                    }
                    // δίνουμε 10 πόντους ως επιβράβευση για τη νέα παραγγελία
                    currentCustomer.addPoints(10); 
                    
                    DatabaseManager.deleteUser(currentCustomer.getEmail());
                    DatabaseManager.saveUser(currentCustomer);
                    
                    String storeName = "Κατάστημα eFood";
                    if (!myCart.isEmpty()) storeName = myCart.get(0)[0].split(" - ")[0]; 
                    
                    // υπολογίζουμε την αμοιβή του διανομέα (15% της παραγγελίας, με ελάχιστο τα 2 ευρώ)
                    double fee = totalAmount * 0.15; 
                    String rewardStr = String.format(java.util.Locale.US, "%.2f", fee < 2.0 ? 2.0 : fee) + "€";
                    
                    String deliveryAddress = currentCustomer.getAddress();
                    if (!currentCustomer.getSavedAddresses().isEmpty()) {
                        for(String ad : currentCustomer.getSavedAddresses()) {
                            if (ad.contains("(Προεπιλογή)")) { deliveryAddress = ad.replace(" (Προεπιλογή)", ""); break; }
                        }
                        if (deliveryAddress.equals(currentCustomer.getAddress())) deliveryAddress = currentCustomer.getSavedAddresses().get(0).replace(" (Προεπιλογή)", "");
                    }
                    
                    // Στέλνουμε και το τηλέφωνο του πελάτη στο DatabaseManager
                    DatabaseManager.saveOrder(orderId, storeName, deliveryAddress, rewardStr, currentCustomer.getPhoneNumber());
                    
                    JOptionPane.showMessageDialog(CartCheckoutFrame.this, "Η πληρωμή εγκρίθηκε!\nΗ παραγγελία εστάλη επιτυχώς!\nΜπορείς να τη δεις στο 'Ενεργή Παραγγελία'.");
                    dispose();
                    new MainDashboardFrame(currentCustomer, new ArrayList<>()).setVisible(true);
                });
            });
            paymentThread.start(); 
        });

        JButton backBtn = new JButton("← Πίσω (Επεξεργασία Καλαθιού)");
        backBtn.setBackground(new Color(255, 180, 180));
        backBtn.setPreferredSize(new Dimension(0, 40));
        
        backBtn.addActionListener(e -> {
            dispose();
            new MainDashboardFrame(currentCustomer, myCart).setVisible(true);
        });

        bottomPanel.add(backBtn, BorderLayout.NORTH);
        bottomPanel.add(finishBtn, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateTotals() {
        double discountAmount = totalAmount * discountRate;
        double finalPay = totalAmount - discountAmount - loyaltyDiscount;
        if (finalPay < 0) finalPay = 0.0; 
        
        if (discountAmount > 0 || loyaltyDiscount > 0) {
            finalTotal.setText("Σύνολο (Με έκπτωση): " + String.format(java.util.Locale.US, "%.2f", finalPay) + "€");
        } else {
            finalTotal.setText("Σύνολο: " + String.format(java.util.Locale.US, "%.2f", finalPay) + "€");
        }
        
        finishBtn.setText("ΟΛΟΚΛΗΡΩΣΗ ΠΑΡΑΓΓΕΛΙΑΣ (" + String.format(java.util.Locale.US, "%.2f", finalPay) + "€)");
    }
}