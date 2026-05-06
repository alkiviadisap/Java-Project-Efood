package efood.gui;

import javax.swing.*;
import java.awt.*;

public class CartCheckoutFrame extends JFrame {

    public CartCheckoutFrame() {
        initComponents();
    }

    private void initComponents() {
        // Ρυθμίσεις Παραθύρου
        setTitle("Καλάθι & Checkout");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2. Κεντρικό Πάνελ (Main Content)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // Χρήση null layout για ταύτιση με το mockup
        mainPanel.setBackground(Color.WHITE);

        // Your Order Label
        JLabel orderLabel = new JLabel("Your Order (ArrayList)");
        orderLabel.setFont(new Font("Arial", Font.BOLD, 13));
        orderLabel.setBounds(30, 20, 300, 20);
        mainPanel.add(orderLabel);

        // Order List Display (JTextArea/Placeholder)
        JPanel orderDisplayPanel = new JPanel(null);
        orderDisplayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        orderDisplayPanel.setBackground(Color.WHITE);
        
        JLabel item1 = new JLabel("1x Burger .......... 5.50€ [X]");
        item1.setBounds(20, 30, 300, 20);
        JLabel item2 = new JLabel("1x Pizza .......... 8.00€ [X]");
        item2.setBounds(20, 55, 300, 20);
        
        orderDisplayPanel.add(item1);
        orderDisplayPanel.add(item2);
        orderDisplayPanel.setBounds(30, 50, 420, 250);
        mainPanel.add(orderDisplayPanel);

        // Promo Code Section
        JLabel promoLabel = new JLabel("Promo Code (HashSet check)");
        promoLabel.setBounds(30, 320, 300, 20);
        mainPanel.add(promoLabel);

        JTextField promoField = new JTextField("Enter Code (JTextField)");
        promoField.setBounds(30, 350, 250, 40);
        mainPanel.add(promoField);

        JButton applyBtn = new JButton("APPLY (JButton)");
        applyBtn.setBackground(new Color(210, 225, 250)); // Απαλό μπλε
        applyBtn.setBounds(290, 350, 160, 40);
        mainPanel.add(applyBtn);

        // Total Price Display
        JLabel totalLabel = new JLabel("Total (calculateFinalPrice()): 13.50€");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setBounds(30, 450, 400, 30);
        mainPanel.add(totalLabel);

        // CHECKOUT Button (Κάτω μέρος)
        JButton checkoutBtn = new JButton("CHECKOUT (JButton)");
        checkoutBtn.setBackground(new Color(248, 206, 204)); // Απαλό ροζ
        checkoutBtn.setFont(new Font("Arial", Font.BOLD, 14));
        checkoutBtn.setBounds(30, 600, 420, 60);
        mainPanel.add(checkoutBtn);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CartCheckoutFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new CartCheckoutFrame().setVisible(true);
        });
    }
}