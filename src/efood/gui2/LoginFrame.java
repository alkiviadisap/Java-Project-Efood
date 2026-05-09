package efood.gui2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        // Ρυθμίσεις Παραθύρου - Laptop Layout
        setTitle("Login & Register");
        setSize(950, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Κύριο Panel με κεντραρισμένο layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(40, 250, 40, 250)); // Πολύ πλατιά περιθώρια για laptop

        // --- Header ---
        JLabel headerLabel = new JLabel("Καλωσήρθατε");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Πεδία Login ---
        addLabeledInput(mainPanel, "Email:");
        addLabeledInput(mainPanel, "Password:");

        // --- CAPTCHA Section ---
        JLabel captchaLabel = new JLabel("Captcha");
        captchaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(captchaLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel captchaBox = new JPanel();
        captchaBox.setMaximumSize(new Dimension(350, 60));
        captchaBox.setBackground(new Color(78, 150, 150)); // Το πετρόλ χρώμα από το mockup
        captchaBox.setBorder(new LineBorder(Color.GRAY));
        mainPanel.add(captchaBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextField captchaInput = new JTextField();
        captchaInput.setMaximumSize(new Dimension(350, 35));
        captchaInput.setHorizontalAlignment(JTextField.CENTER);
        mainPanel.add(captchaInput);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Login Button ---
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(239, 68, 68)); // Κόκκινο
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setMaximumSize(new Dimension(350, 45));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setFocusPainted(false);
        mainPanel.add(loginBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Registration Section ---
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(400, 1));
        mainPanel.add(sep);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel registerAsLabel = new JLabel("Register as:");
        registerAsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(registerAsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        String[] roles = {"Πελάτης", "Διανομέας", "Κατάστημα"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setMaximumSize(new Dimension(350, 40));
        mainPanel.add(roleCombo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- Register Button ---
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(100, 200, 80)); // Πράσινο
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        registerBtn.setMaximumSize(new Dimension(350, 45));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setFocusPainted(false);
        mainPanel.add(registerBtn);

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
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}