package efood.gui;

import javax.swing.*;
import java.awt.*;

public class MainDashboardFrame extends JFrame {

    public MainDashboardFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("e-Food Main Dashboard");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        // 2. Κεντρικό Πάνελ (Main Content)
        JPanel mainContent = new JPanel(null); // Null layout για απόλυτη τοποθέτηση στοιχείων
        mainContent.setBackground(Color.WHITE);

        // Search Bar & Cart Button
        JTextField searchField = new JTextField("Search Stores (JTextField)");
        searchField.setBounds(20, 20, 300, 40);
        mainContent.add(searchField);

        JButton cartBtn = new JButton("🛒 (JButton)");
        cartBtn.setBackground(new Color(248, 206, 204));
        cartBtn.setBounds(330, 20, 120, 40);
        mainContent.add(cartBtn);

        // Recommended Section
        JLabel recLabel = new JLabel("Recommended for you (Multithreading)");
        recLabel.setForeground(Color.RED);
        recLabel.setFont(new Font("Arial", Font.BOLD, 12));
        recLabel.setBounds(20, 80, 300, 20);
        mainContent.add(recLabel);

        // Product Card (Burger)
        JPanel productCard = new JPanel(new GridBagLayout());
        productCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        productCard.setBackground(new Color(255, 250, 205)); // Απαλό κίτρινο
        JLabel productText = new JLabel("<html><center>Product Card<br>Burger - 5.50€</center></html>");
        productCard.add(productText);
        productCard.setBounds(20, 110, 120, 120);
        mainContent.add(productCard);

        // Available Stores Section
        JLabel storesLabel = new JLabel("Available Stores (ArrayList)");
        storesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        storesLabel.setBounds(20, 260, 300, 20);
        mainContent.add(storesLabel);

        // Store Card
        JPanel storeCard = new JPanel(new GridBagLayout());
        storeCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        storeCard.add(new JLabel("Store Card: 'The Pizza Place'"));
        storeCard.setBounds(20, 290, 430, 60);
        mainContent.add(storeCard);

        // 3. Lucky Pinata (Στρογγυλό κουμπί κάτω δεξιά)
        JButton pinataBtn = new JButton("<html><center>Lucky<br>Pinata</center></html>");
        pinataBtn.setBackground(new Color(230, 200, 250)); // Μωβ
        pinataBtn.setFocusPainted(false);
        pinataBtn.setBounds(350, 500, 80, 80);
        // Προαιρετικό: Κάνει το κουμπί να φαίνεται πιο στρογγυλό
        pinataBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        mainContent.add(pinataBtn);

        add(mainContent, BorderLayout.CENTER);
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
            java.util.logging.Logger.getLogger(MainDashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new MainDashboardFrame().setVisible(true);
        });
    }
}