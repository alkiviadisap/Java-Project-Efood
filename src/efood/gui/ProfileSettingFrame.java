package efood.gui;

import efood.models.Customer;
import efood.utils.DatabaseManager;
import efood.main.EfoodApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class ProfileSettingFrame extends JFrame {
    
    private Customer currentCustomer;
    private ArrayList<String[]> myCart; 
    private JComboBox<String> cardsCombo; 

    public ProfileSettingFrame(Customer customer, ArrayList<String[]> cart) {
        this.currentCustomer = customer;
        this.myCart = cart;
        
        setTitle("Ρυθμίσεις Προφίλ");
        setSize(950, 850); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(20, 200, 20, 200)); 

        addSectionTitle(mainPanel, "Προσωπικά Στοιχεία");
        addFormRow(mainPanel, "Ονοματεπώνυμο:", currentCustomer.getFullName(), true);
        addFormRow(mainPanel, "Email (Δεν αλλάζει):", currentCustomer.getEmail(), false);
        addFormRow(mainPanel, "Τηλέφωνο:", currentCustomer.getPhoneNumber(), true);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        addSectionTitle(mainPanel, "Διευθύνσεις");
        JComboBox<String> addrCombo = new JComboBox<>();
        if (currentCustomer.getSavedAddresses().isEmpty()) {
            addrCombo.addItem(currentCustomer.getAddress());
        } else {
            for (String addr : currentCustomer.getSavedAddresses()) {
                addrCombo.addItem(addr);
            }
        }
        styleComponent(addrCombo);
        addrCombo.setBackground(Color.WHITE);
        mainPanel.add(addrCombo);
        
        JPanel addrButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        addrButtonsPanel.setOpaque(false);
        JButton addAddrBtn = createSmallButton("+ Νέα", new Color(100, 200, 255), Color.BLACK);
        JButton setDefaultAddrBtn = createSmallButton("⭐ Προεπιλογή", new Color(255, 215, 0), Color.BLACK);
        JButton removeAddrBtn = createSmallButton("✖ Διαγραφή", new Color(255, 100, 100), Color.WHITE);
        addrButtonsPanel.add(addAddrBtn);
        addrButtonsPanel.add(setDefaultAddrBtn);
        addrButtonsPanel.add(removeAddrBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(addrButtonsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        addAddrBtn.addActionListener(e -> {
            // ανοίγει ένα input dialog για να γράψει τη νέα διεύθυνση
            String newAddr = JOptionPane.showInputDialog(this, "Προσθήκη Νέας Διεύθυνσης:");
            if (newAddr != null && !newAddr.trim().isEmpty()) {
                addrCombo.addItem(newAddr.trim().replace(",", " "));
                addrCombo.setSelectedIndex(addrCombo.getItemCount() - 1);
            }
        });
        
        removeAddrBtn.addActionListener(e -> {
            int selectedIdx = addrCombo.getSelectedIndex();
            // έλεγχος για να μην σβήσει την τελευταία διεύθυνση που έχει μείνει
            if (selectedIdx != -1 && addrCombo.getItemCount() > 1) { 
                addrCombo.removeItemAt(selectedIdx);
            } else if (addrCombo.getItemCount() <= 1) {
                JOptionPane.showMessageDialog(this, "Πρέπει να υπάρχει τουλάχιστον μία διεύθυνση!");
            }
        });
        
        setDefaultAddrBtn.addActionListener(e -> {
            int selectedIdx = addrCombo.getSelectedIndex();
            if (selectedIdx != -1) {
                // ψάχνει να βρει την παλιά προεπιλογή, της αφαιρεί το tag, και το βάζει στη νέα
                String currentText = addrCombo.getItemAt(selectedIdx);
                if (!currentText.contains("(Προεπιλογή)")) {
                    for (int i = 0; i < addrCombo.getItemCount(); i++) {
                        String item = addrCombo.getItemAt(i);
                        if (item.contains("(Προεπιλογή)")) {
                            addrCombo.removeItemAt(i);
                            addrCombo.insertItemAt(item.replace(" (Προεπιλογή)", ""), i);
                        }
                    }
                    addrCombo.removeItemAt(selectedIdx);
                    addrCombo.insertItemAt(currentText + " (Προεπιλογή)", selectedIdx);
                    addrCombo.setSelectedIndex(selectedIdx);
                }
            }
        });

        addSectionTitle(mainPanel, "💳 Κάρτες");
        cardsCombo = new JComboBox<>();
        if (currentCustomer.getSavedCards().isEmpty()) {
            cardsCombo.addItem("Καμία αποθηκευμένη κάρτα.");
        } else {
            for (String card : currentCustomer.getSavedCards()) {
                cardsCombo.addItem(card);
            }
        }
        styleComponent(cardsCombo);
        cardsCombo.setBackground(Color.WHITE);
        mainPanel.add(cardsCombo);

        JPanel cardButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        cardButtonsPanel.setOpaque(false);
        JButton addCardBtn = createSmallButton("+ Νέα", new Color(100, 200, 255), Color.BLACK);
        JButton setDefaultCardBtn = createSmallButton("⭐ Προεπιλογή", new Color(255, 215, 0), Color.BLACK);
        JButton removeCardBtn = createSmallButton("✖ Διαγραφή", new Color(255, 100, 100), Color.WHITE);
        cardButtonsPanel.add(addCardBtn);
        cardButtonsPanel.add(setDefaultCardBtn);
        cardButtonsPanel.add(removeCardBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(cardButtonsPanel);

        addCardBtn.addActionListener(e -> {
            // φτιάχνει ένα custom dialog με πολλά πεδία για τα στοιχεία της κάρτας
            JTextField cardNumField = new JTextField();
            JTextField expField = new JTextField();
            JTextField cvvField = new JTextField();
            Object[] message = {
                "Αριθμός Κάρτας (16 ψηφία):", cardNumField,
                "Ημερομηνία Λήξης (MM/YY):", expField,
                "CVV:", cvvField
            };
            int option = JOptionPane.showConfirmDialog(this, message, "Προσθήκη Κάρτας", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String cardNum = cardNumField.getText().trim();
                String cvv = cvvField.getText().trim();
                // απλός έλεγχος για το αν έβαλε νούμερα και δεν είναι άδειο
                if (cardNum.length() >= 4 && !cvv.isEmpty() && cardNum.matches("\\d+")) {
                    if (cardsCombo.getItemAt(0).equals("Καμία αποθηκευμένη κάρτα.")) {
                        cardsCombo.removeAllItems();
                    }
                    String last4 = cardNum.substring(cardNum.length() - 4);
                    cardsCombo.addItem("Κάρτα **** " + last4);
                    cardsCombo.setSelectedIndex(cardsCombo.getItemCount() - 1); 
                } else {
                    JOptionPane.showMessageDialog(this, "Λάθος στοιχεία κάρτας.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeCardBtn.addActionListener(e -> {
            int selectedIdx = cardsCombo.getSelectedIndex();
            if (selectedIdx != -1 && !cardsCombo.getItemAt(0).equals("Καμία αποθηκευμένη κάρτα.")) {
                cardsCombo.removeItemAt(selectedIdx);
                if (cardsCombo.getItemCount() == 0) {
                    cardsCombo.addItem("Καμία αποθηκευμένη κάρτα.");
                }
            }
        });

        setDefaultCardBtn.addActionListener(e -> {
            int selectedIdx = cardsCombo.getSelectedIndex();
            if (selectedIdx != -1 && !cardsCombo.getItemAt(0).equals("Καμία αποθηκευμένη κάρτα.")) {
                // ίδια λογική με τις διευθύνσεις, αλλάζει το tag (Προεπιλογή)
                String currentText = cardsCombo.getItemAt(selectedIdx);
                if (!currentText.contains("(Προεπιλογή)")) {
                    for (int i = 0; i < cardsCombo.getItemCount(); i++) {
                        String item = cardsCombo.getItemAt(i);
                        if (item.contains("(Προεπιλογή)")) {
                            cardsCombo.removeItemAt(i);
                            cardsCombo.insertItemAt(item.replace(" (Προεπιλογή)", ""), i);
                        }
                    }
                    cardsCombo.removeItemAt(selectedIdx);
                    cardsCombo.insertItemAt(currentText + " (Προεπιλογή)", selectedIdx);
                    cardsCombo.setSelectedIndex(selectedIdx);
                }
            }
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel loyaltyLabel = new JLabel("Πόντοι Loyalty: " + currentCustomer.getLoyaltyPoints() + " 🎁");
        loyaltyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        loyaltyLabel.setForeground(new Color(50, 150, 50));
        loyaltyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(loyaltyLabel);

        mainPanel.add(Box.createVerticalGlue()); 

        JButton saveBtn = createLargeButton("Αποθήκευση", new Color(150, 255, 150), Color.BLACK);
        saveBtn.addActionListener(e -> {
            // μαζεύει όλα τα items από τα combobox (κάρτες, διευθύνσεις) και τα σώζει
            ArrayList<String> newCards = new ArrayList<>();
            if (!cardsCombo.getItemAt(0).equals("Καμία αποθηκευμένη κάρτα.")) {
                for (int i = 0; i < cardsCombo.getItemCount(); i++) {
                    newCards.add(cardsCombo.getItemAt(i));
                }
            }
            currentCustomer.setSavedCards(newCards);
            DatabaseManager.updateCustomerCards(currentCustomer.getEmail(), newCards);
            
            ArrayList<String> newAddrs = new ArrayList<>();
            for (int i = 0; i < addrCombo.getItemCount(); i++) {
                newAddrs.add(addrCombo.getItemAt(i));
            }
            currentCustomer.setSavedAddresses(newAddrs);
            DatabaseManager.updateCustomerAddresses(currentCustomer.getEmail(), newAddrs);

            JOptionPane.showMessageDialog(this, "Αποθηκεύτηκαν!");
        });
        
        mainPanel.add(saveBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel midActions = new JPanel(new GridLayout(1, 2, 15, 0));
        midActions.setOpaque(false);
        midActions.setMaximumSize(new Dimension(500, 50));
        
        JButton backBtn = createLargeButton("Επιστροφή", new Color(200, 200, 200), Color.BLACK);
        backBtn.addActionListener(e -> {
            dispose();
            new MainDashboardFrame(currentCustomer, myCart).setVisible(true);
        });
        
        JButton signOutBtn = createLargeButton("Αποσύνδεση", new Color(255, 180, 80), Color.BLACK);
        signOutBtn.addActionListener(e -> {
            EfoodApp.clearSession();
            dispose();
            new LoginFrame().setVisible(true);
        });
        
        midActions.add(backBtn);
        midActions.add(signOutBtn);
        mainPanel.add(midActions);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        add(mainPanel, BorderLayout.CENTER);
    }

    private void addFormRow(JPanel panel, String labelText, String value, boolean isEditable) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);
        
        JTextField field = new JTextField(value);
        field.setEditable(isEditable);
        if (!isEditable) field.setForeground(Color.GRAY);
        styleComponent(field);
        field.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addSectionTitle(JPanel panel, String title) {
        JLabel label = new JLabel(title);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(50, 50, 80));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
    }

    // κοινό στυλ για τα components για να μην το γράφω παντού
    private void styleComponent(JComponent c) {
        c.setMaximumSize(new Dimension(500, 40));
        c.setPreferredSize(new Dimension(500, 40));
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
        c.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private JButton createLargeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setMaximumSize(new Dimension(500, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    private JButton createSmallButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(150, 35));
        return btn;
    }
}