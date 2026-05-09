package efood.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StoreOwnerRegistrationFrame extends JFrame {

    public StoreOwnerRegistrationFrame() {
        // Ρυθμίσεις Παραθύρου - Laptop Layout
        setTitle("Store Owner Registration Frame");
        setSize(950, 850); // Αυξημένο ύψος για να χωρέσει άνετα όλα τα πεδία
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Κύριο Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(40, 250, 40, 250)); // Margins για laptop

        // --- Header ---
        JLabel headerLabel = new JLabel("Εγγραφή Ιδιοκτήτη Καταστήματος");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Πεδία Εισαγωγής ---
        addLabeledInput(mainPanel, "Email:");
        addLabeledInput(mainPanel, "Password:");
        addLabeledInput(mainPanel, "Store Address:");
        addLabeledInput(mainPanel, "Phone Number:");
        addLabeledInput(mainPanel, "Vat Number:");

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- CAPTCHA Section ---
        JPanel captchaWrapper = new JPanel(new GridLayout(1, 2, 10, 0));
        captchaWrapper.setMaximumSize(new Dimension(450, 50));
        captchaWrapper.setOpaque(false);
        
        JLabel captchaBox = new JLabel("CAPTCHA", SwingConstants.CENTER);
        captchaBox.setOpaque(true);
        captchaBox.setBackground(new Color(78, 150, 150)); // Το πετρόλ χρώμα από το mockup
        captchaBox.setForeground(Color.WHITE);
        captchaBox.setFont(new Font("Monospaced", Font.BOLD, 16));
        
        JTextField captchaInput = new JTextField();
        captchaInput.setBorder(new LineBorder(Color.LIGHT_GRAY));
        
        captchaWrapper.add(captchaBox);
        captchaWrapper.add(captchaInput);
        mainPanel.add(captchaWrapper);

        // --- Info Note ---
        JLabel infoNote = new JLabel("<html><center>*The information will be verified to<br>ensure the validity of the Vat Number</center></html>");
        infoNote.setFont(new Font("Arial", Font.ITALIC, 12));
        infoNote.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(infoNote);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Submit Button ---
        JButton submitBtn = new JButton("Submit Application");
        submitBtn.setBackground(new Color(100, 255, 100)); // Πράσινο
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setMaximumSize(new Dimension(450, 55));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setFocusPainted(false);
        mainPanel.add(submitBtn);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Back to Login Link ---
        JButton backBtn = new JButton("Back to Login");
        backBtn.setForeground(Color.BLUE);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(backBtn);

        add(mainPanel, BorderLayout.CENTER);
    }

    // Βοηθητική μέθοδος για Labels και Fields
    private void addLabeledInput(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(450, 40));
        field.setPreferredSize(new Dimension(450, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(0, 10, 0, 10)));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } 
        catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new StoreOwnerRegistrationFrame().setVisible(true));
    }
}