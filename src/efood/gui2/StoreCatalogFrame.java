package efood.gui2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StoreCatalogFrame extends JFrame {

    public StoreCatalogFrame() {
        // Ρυθμίσεις Παραθύρου - Laptop Layout
        setTitle("Κατάλογος Καταστήματος");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Κύριο Panel με padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 0));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        // --- Πάνω Μέρος: Τίτλος Καταστήματος ---
        JLabel storeName = new JLabel("NAME OF STORE", SwingConstants.CENTER);
        storeName.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 28));
        storeName.setBorder(new EmptyBorder(10, 0, 20, 0));
        add(storeName, BorderLayout.NORTH);

        // --- Αριστερό Panel: Λίστα Προϊόντων (Menu) ---
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setOpaque(false);
        
        JPanel menuHeader = new JPanel(new BorderLayout());
        menuHeader.setOpaque(false);
        JButton backBtn = new JButton("Go Back");
        backBtn.setBackground(new Color(230, 80, 80));
        backBtn.setForeground(Color.WHITE);
        
        JButton addBtn = new JButton("Add to Basket");
        addBtn.setBackground(new Color(255, 220, 220));
        
        menuHeader.add(backBtn, BorderLayout.WEST);
        menuHeader.add(addBtn, BorderLayout.EAST);
        menuHeader.setBorder(new EmptyBorder(0, 0, 10, 0));

        JTextArea productList = new JTextArea("Item 1\nItem 2\nItem 3\nItem 4\nItem 5");
        productList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        productList.setBorder(new LineBorder(Color.BLACK, 2));
        
        menuPanel.add(menuHeader, BorderLayout.NORTH);
        menuPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

        // --- Δεξί Panel: My Cart ---
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setPreferredSize(new Dimension(350, 0));
        cartPanel.setOpaque(false);

        JLabel cartTitle = new JLabel("Το Καλάθι μου:");
        cartTitle.setFont(new Font("Arial", Font.BOLD, 18));
        cartTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel cartItemsBox = new JPanel();
        cartItemsBox.setBackground(Color.WHITE);
        cartItemsBox.setBorder(new LineBorder(Color.BLACK, 2));
        cartItemsBox.setPreferredSize(new Dimension(300, 300));
        cartItemsBox.setMaximumSize(new Dimension(300, 300));

        // --- Loyalty Points Section (Προστέθηκε ο αριθμός των πόντων) ---
        JPanel loyaltyPanel = new JPanel();
        loyaltyPanel.setLayout(new BoxLayout(loyaltyPanel, BoxLayout.Y_AXIS));
        loyaltyPanel.setOpaque(false);
        loyaltyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel currentPointsLabel = new JLabel("Your Points: 150"); // Εδώ φαίνονται οι πόντοι
        currentPointsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        currentPointsLabel.setForeground(new Color(120, 40, 180)); // Μοβ χρώμα για έμφαση
        currentPointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JCheckBox loyaltyCheck = new JCheckBox("Redeem loyalty points?");
        loyaltyCheck.setFont(new Font("Arial", Font.PLAIN, 14));
        loyaltyCheck.setOpaque(false);
        loyaltyCheck.setAlignmentX(Component.CENTER_ALIGNMENT);

        loyaltyPanel.add(currentPointsLabel);
        loyaltyPanel.add(loyaltyCheck);
        
        JLabel totalLabel = new JLabel("Total Amount: 16.50€");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalLabel.setBorder(new EmptyBorder(15, 0, 0, 0));

        cartPanel.add(cartTitle);
        cartPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cartPanel.add(cartItemsBox);
        cartPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cartPanel.add(loyaltyPanel);
        cartPanel.add(totalLabel);

        // --- Layout Integration ---
        mainPanel.add(menuPanel, BorderLayout.CENTER);
        mainPanel.add(cartPanel, BorderLayout.EAST);

        // --- Bottom Button: NEXT ---
        JButton nextBtn = new JButton("NEXT TO CHECKOUT");
        nextBtn.setBackground(new Color(150, 255, 150)); // Πράσινο
        nextBtn.setFont(new Font("Arial", Font.BOLD, 20));
        nextBtn.setPreferredSize(new Dimension(0, 70));
        
        add(mainPanel, BorderLayout.CENTER);
        add(nextBtn, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } 
        catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new StoreCatalogFrame().setVisible(true));
    }
}