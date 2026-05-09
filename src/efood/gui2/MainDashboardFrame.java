package efood.gui2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainDashboardFrame extends JFrame {

    public MainDashboardFrame() {
        // Ρυθμίσεις Παραθύρου - Laptop Wide Layout
        setTitle("Main Dashboard");
        setSize(1000, 800); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Κύριο Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 242, 245));
        mainPanel.setBorder(new EmptyBorder(25, 100, 25, 100)); // Margins για laptop

        // 1. Header Area (User Info & Settings)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel userLabel = new JLabel("👤 user@gmail.com");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton settingsBtn = new JButton("My Settings");
        settingsBtn.setPreferredSize(new Dimension(120, 35));
        
        headerPanel.add(userLabel, BorderLayout.WEST);
        headerPanel.add(settingsBtn, BorderLayout.EAST);
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // 2. Search Section
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        searchPanel.setOpaque(false);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JLabel searchTitle = new JLabel("Search Stores:");
        searchTitle.setFont(new Font("Arial", Font.BOLD, 13));
        
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(350, 35));
        
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(255, 120, 120)); // Απαλό κόκκινο
        searchBtn.setPreferredSize(new Dimension(100, 35));
        
        searchPanel.add(searchTitle);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        mainPanel.add(searchPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // 3. Recommended Section
        JLabel recLabel = new JLabel("Recommended For you:");
        recLabel.setFont(new Font("Arial", Font.BOLD, 15));
        recLabel.setForeground(new Color(220, 50, 50)); // Κόκκινο χρώμα τίτλου
        recLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(recLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel productCard = new JPanel();
        productCard.setLayout(new GridLayout(2, 1));
        productCard.setBackground(new Color(255, 253, 208)); // Κρεμ/Κίτρινο
        productCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 210, 150), 1),
                new EmptyBorder(10, 20, 10, 20)));
        productCard.setMaximumSize(new Dimension(400, 80));
        
        JLabel prodTitle = new JLabel("Product Card", SwingConstants.CENTER);
        prodTitle.setFont(new Font("Arial", Font.ITALIC, 13));
        JLabel prodInfo = new JLabel("Burger – 5.50€", SwingConstants.CENTER);
        prodInfo.setFont(new Font("Arial", Font.BOLD, 14));
        
        productCard.add(prodTitle);
        productCard.add(prodInfo);
        mainPanel.add(productCard);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // 4. Available Stores Section
        JLabel storesLabel = new JLabel("Available Stores:");
        storesLabel.setFont(new Font("Arial", Font.BOLD, 22));
        storesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(storesLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Μεγάλο Panel για τη λίστα καταστημάτων
        JPanel storesListPanel = new JPanel();
        storesListPanel.setBackground(Color.WHITE);
        storesListPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollPane = new JScrollPane(storesListPanel);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        mainPanel.add(scrollPane);
        
        mainPanel.add(Box.createVerticalGlue());

        // 5. Footer (Loyalty Points)
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        footerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JLabel loyaltyLabel = new JLabel("Loyalty Points: 150");
        loyaltyLabel.setFont(new Font("Arial", Font.BOLD, 13));
        
        JButton pinataBtn = new JButton("Lucky Pinata 🪅");
        pinataBtn.setBackground(new Color(220, 50, 220)); // Μοβ/Φούξια
        pinataBtn.setForeground(Color.BLACK);
        pinataBtn.setPreferredSize(new Dimension(150, 40));
        
        footerPanel.add(loyaltyLabel, BorderLayout.WEST);
        footerPanel.add(pinataBtn, BorderLayout.EAST);
        mainPanel.add(footerPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new MainDashboardFrame().setVisible(true);
        });
    }
}