package efood.gui2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CartCheckoutFrame extends JFrame {

    public CartCheckoutFrame() {
        // 1. Βασικές ρυθμίσεις παραθύρου (Ομοιόμορφο μέγεθος Laptop Layout)
        setTitle("Καλάθι & Checkout");
        setSize(1000, 800); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // 2. Κύριο Panel με μεγάλα περιθώρια (100px δεξιά-αριστερά) για ομοιομορφία
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(30, 100, 20, 100));

        // --- Τίτλος Ενότητας ---
        JLabel orderLabel = new JLabel("Η Παραγγελία σας:");
        orderLabel.setFont(new Font("Arial", Font.BOLD, 20));
        orderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(orderLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Πίνακας Προϊόντων ---
        String[] columns = {"Προϊόν", "Ποσότητα", "Τιμή Μονάδος", "Σύνολο"};
        Object[][] data = {
            {"Burger", "1", "5.50€", "5.50€"},
            {"Pizza", "1", "8.00€", "8.00€"},
            {"Coca Cola", "2", "1.50€", "3.00€"}
        };

        JTable table = new JTable(new DefaultTableModel(data, columns));
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setMaximumSize(new Dimension(800, 200));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Promo Code Section ---
        JPanel promoPanel = new JPanel(new BorderLayout(15, 0));
        promoPanel.setOpaque(false);
        promoPanel.setMaximumSize(new Dimension(600, 40));
        
        JTextField promoField = new JTextField("Promo Code...");
        JButton applyBtn = new JButton("Apply");
        applyBtn.setBackground(new Color(200, 200, 200));
        
        promoPanel.add(promoField, BorderLayout.CENTER);
        promoPanel.add(applyBtn, BorderLayout.EAST);
        mainPanel.add(promoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Loyalty & Payment ---
        JCheckBox loyaltyCheck = new JCheckBox("Εξαργύρωση πόντων Loyalty");
        loyaltyCheck.setOpaque(false);
        loyaltyCheck.setFont(new Font("Arial", Font.PLAIN, 14));
        loyaltyCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(loyaltyCheck);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel paymentRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        paymentRow.setOpaque(false);
        paymentRow.add(new JLabel("Τρόπος Πληρωμής:"));
        paymentRow.add(new JComboBox<>(new String[]{"Πιστωτική Κάρτα", "PayPal", "Αντικαταβολή"}));
        paymentRow.setMaximumSize(new Dimension(600, 40));
        mainPanel.add(paymentRow);
        
        mainPanel.add(Box.createVerticalGlue());

        // --- Σύνολα (Totals) ---
        JPanel totalsPanel = new JPanel();
        totalsPanel.setLayout(new BoxLayout(totalsPanel, BoxLayout.Y_AXIS));
        totalsPanel.setOpaque(false);
        totalsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel totalLabel = new JLabel("Τελικό Ποσό: 16.50€");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel discountLabel = new JLabel("Εκπτώσεις: 0.00€");
        discountLabel.setForeground(Color.GRAY);
        discountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        totalsPanel.add(totalLabel);
        totalsPanel.add(discountLabel);
        mainPanel.add(totalsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // --- Checkout Button (South) ---
        JButton checkoutBtn = new JButton("ΟΛΟΚΛΗΡΩΣΗ ΠΑΡΑΓΓΕΛΙΑΣ");
        checkoutBtn.setBackground(new Color(255, 175, 175)); // Σομόν
        checkoutBtn.setFont(new Font("Arial", Font.BOLD, 18));
        checkoutBtn.setPreferredSize(new Dimension(1000, 70));
        checkoutBtn.setOpaque(true);
        checkoutBtn.setBorderPainted(false);
        
        add(mainPanel, BorderLayout.CENTER);
        add(checkoutBtn, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new CartCheckoutFrame().setVisible(true));
    }
}