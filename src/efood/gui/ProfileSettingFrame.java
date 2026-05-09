package efood.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ProfileSettingFrame extends JFrame {

    public ProfileSettingFrame() {
        // Ρυθμίσεις Παραθύρου - Laptop Layout
        setTitle("Profile Settings");
        setSize(950, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Κύριο Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 242, 245));
        mainPanel.setBorder(new EmptyBorder(30, 250, 30, 250)); // Margins για συγκέντρωση στο κέντρο

        // --- Στοιχεία Χρήστη ---
        addFormRow(mainPanel, "Full Name:");
        addFormRow(mainPanel, "Email:");
        addFormRow(mainPanel, "Phone Number:");

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Addresses Section ---
        addSectionTitle(mainPanel, "Addresses:");
        String[] addresses = {"Κεντρική Διεύθυνση", "Εργασία", "Εξοχικό"};
        JComboBox<String> addrCombo = new JComboBox<>(addresses);
        styleComponent(addrCombo);
        mainPanel.add(addrCombo);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton addAddrBtn = new JButton("Add New Address");
        addAddrBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(addAddrBtn);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // --- My Cards Section ---
        addSectionTitle(mainPanel, "My Cards:");
        String[] cards = {"Visa **** 1234", "Mastercard **** 5678"};
        JComboBox<String> cardCombo = new JComboBox<>(cards);
        styleComponent(cardCombo);
        mainPanel.add(cardCombo);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel cardButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        cardButtons.setOpaque(false);
        cardButtons.add(new JButton("Add a Card"));
        cardButtons.add(new JButton("Set as Default"));
        mainPanel.add(cardButtons);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Loyalty Points ---
        JLabel loyaltyLabel = new JLabel("Loyalty Points: 450");
        loyaltyLabel.setFont(new Font("Arial", Font.BOLD, 15));
        loyaltyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(loyaltyLabel);

        mainPanel.add(Box.createVerticalGlue()); // Σπρώχνει τα κουμπιά δράσης κάτω

        // --- Action Buttons ---
        // Save Changes (Πράσινο)
        JButton saveBtn = createActionButton("Save Changes", new Color(120, 255, 100));
        mainPanel.add(saveBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel για Go Back & Sign Out
        JPanel midActions = new JPanel(new GridLayout(1, 2, 10, 0));
        midActions.setOpaque(false);
        midActions.setMaximumSize(new Dimension(450, 45));
        
        JButton backBtn = createActionButton("Go Back", new Color(255, 100, 100));
        JButton signOutBtn = createActionButton("Sign Out", new Color(255, 180, 80));
        
        midActions.add(backBtn);
        midActions.add(signOutBtn);
        mainPanel.add(midActions);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Delete Account (Κόκκινο έντονο)
        JButton deleteBtn = createActionButton("DELETE ACCOUNT", new Color(255, 50, 50));
        deleteBtn.setForeground(Color.WHITE);
        mainPanel.add(deleteBtn);

        add(mainPanel, BorderLayout.CENTER);
    }

    // --- Helper Methods ---
    private void addFormRow(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);
        
        JTextField field = new JTextField();
        styleComponent(field);
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addSectionTitle(JPanel panel, String title) {
        JLabel label = new JLabel(title);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void styleComponent(JComponent c) {
        c.setMaximumSize(new Dimension(450, 35));
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setMaximumSize(new Dimension(450, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } 
        catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> new ProfileSettingFrame().setVisible(true));
    }
}