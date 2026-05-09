package efood.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DelivererRegistrationFrame extends JFrame {

    public DelivererRegistrationFrame() {
        // Ρυθμίσεις Παραθύρου
        setTitle("Αίτηση Νέου Διανομέα");
        setSize(950, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Κύριο Panel με Light Gray φόντο
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 242, 245));
        // Μεγάλα περιθώρια για να έρθει η φόρμα στο κέντρο
        mainPanel.setBorder(new EmptyBorder(40, 220, 40, 220));

        // --- Τίτλος Ενότητας ---
        JLabel titleLabel = new JLabel("Συμπληρώστε τα στοιχεία σας");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Πεδία Εισαγωγής ---
        addFormInput(mainPanel, "Ονοματεπώνυμο:");
        addFormInput(mainPanel, "Email:");
        addFormInput(mainPanel, "Αριθμός Διπλώματος:");

        // --- Τύπος Οχήματος ---
        JLabel vLabel = new JLabel("Τύπος Οχήματος:");
        vLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        vLabel.setFont(new Font("Arial", Font.BOLD, 13));
        mainPanel.add(vLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        String[] vehicles = {"Μοτοσυκλέτα", "Αυτοκίνητο", "Ποδήλατο"};
        JComboBox<String> vehicleCombo = new JComboBox<>(vehicles);
        vehicleCombo.setMaximumSize(new Dimension(500, 40));
        mainPanel.add(vehicleCombo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Κουμπί Upload ---
        JButton uploadBtn = new JButton("📁 Ανέβασμα φωτογραφίας διπλώματος");
        uploadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadBtn.setMaximumSize(new Dimension(500, 40));
        uploadBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(uploadBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- CAPTCHA Section ---
        JPanel captchaWrapper = new JPanel(new GridLayout(1, 2, 10, 0));
        captchaWrapper.setMaximumSize(new Dimension(500, 45));
        captchaWrapper.setOpaque(false);
        
        JLabel captchaBox = new JLabel("A B 7 2 X", SwingConstants.CENTER);
        captchaBox.setOpaque(true);
        captchaBox.setBackground(new Color(200, 250, 220));
        captchaBox.setBorder(new LineBorder(new Color(150, 200, 170), 1));
        captchaBox.setFont(new Font("Monospaced", Font.ITALIC | Font.BOLD, 16));
        
        JTextField captchaInput = new JTextField("Εισάγετε κωδικό...");
        captchaInput.setForeground(Color.GRAY);
        
        captchaWrapper.add(captchaBox);
        captchaWrapper.add(captchaInput);
        mainPanel.add(captchaWrapper);

        // --- Footer Button ---
        JButton submitBtn = new JButton("ΥΠΟΒΟΛΗ ΑΙΤΗΣΗΣ");
        submitBtn.setBackground(new Color(255, 180, 180)); // Πιο "ζωντανό" σομόν
        submitBtn.setForeground(Color.BLACK);
        submitBtn.setPreferredSize(new Dimension(950, 70));
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setFocusPainted(false);
        submitBtn.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        add(mainPanel, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);
    }

    // Βοηθητική μέθοδος για κεντραρισμένα Labels και TextFields
    private void addFormInput(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(label);
        
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(500, 40));
        field.setPreferredSize(new Dimension(500, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(0, 10, 0, 10))); // Padding μέσα στο text field
        panel.add(field);
        
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new DelivererRegistrationFrame().setVisible(true);
        });
    }
}