package efood.gui2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CustomerRegistrationFrame extends JFrame {

    public CustomerRegistrationFrame() {
        // 1. Ρυθμίσεις παραθύρου - Ομοιόμορφο Laptop Layout
        setTitle("Φόρμα Εγγραφής Πελάτη");
        setSize(950, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // 2. Κύριο Panel με μεγάλα περιθώρια (margins) για ομοιομορφία
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        // Margins: 40px πάνω, 220px δεξιά/αριστερά για να δείχνει κεντραρισμένη η φόρμα
        mainPanel.setBorder(new EmptyBorder(40, 220, 20, 220));

        // --- Τίτλος ---
        JLabel titleLabel = new JLabel("Δημιουργία Λογαριασμού");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Προσθήκη Πεδίων ---
        mainPanel.add(createLargeFieldPanel("Email:"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Password:"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Phone number:"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Address:"));

        mainPanel.add(Box.createVerticalGlue());

        // --- Κουμπί Εγγραφής (South) ---
        JButton registerBtn = new JButton("ΕΓΓΡΑΦΗ");
        registerBtn.setBackground(new Color(255, 175, 175)); // Σομόν
        registerBtn.setFont(new Font("Arial", Font.BOLD, 18));
        registerBtn.setPreferredSize(new Dimension(950, 70)); // Ύψος 70px όπως τα άλλα
        registerBtn.setOpaque(true);
        registerBtn.setBorderPainted(false);

        add(mainPanel, BorderLayout.CENTER);
        add(registerBtn, BorderLayout.SOUTH);
    }

    // Βοηθητική μέθοδος για κεντραρισμένα και ομοιόμορφα πεδία
    private JPanel createLargeFieldPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        
        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(500, 45)); 
        textField.setPreferredSize(new Dimension(500, 45));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setBorder(new LineBorder(new Color(170, 180, 200), 2));
        textField.setFont(new Font("Arial", Font.PLAIN, 16));

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(textField);
        
        return panel;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new CustomerRegistrationFrame().setVisible(true);
        });
    }
}