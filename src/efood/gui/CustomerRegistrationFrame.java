package efood.gui2; // ΣΙΓΟΥΡΕΨΟΥ ΟΤΙ ΤΟ PACKAGE EINAI TO ΣΩΣΤΟ ΣΤΟ NETBEANS

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerRegistrationFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passField; 
    private JTextField phoneField;
    private JTextField addressField;

    public CustomerRegistrationFrame() {
        setTitle("Φόρμα Εγγραφής Πελάτη");
        setSize(950, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(40, 220, 20, 220));

        JLabel titleLabel = new JLabel("Δημιουργία Λογαριασμού");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        emailField = new JTextField();
        passField = new JPasswordField();
        phoneField = new JTextField();
        addressField = new JTextField();

        mainPanel.add(createLargeFieldPanel("Email:", emailField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Password:", passField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Phone number:", phoneField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Address:", addressField));

        mainPanel.add(Box.createVerticalGlue());

        JButton registerBtn = new JButton("ΕΓΓΡΑΦΗ");
        registerBtn.setBackground(new Color(255, 175, 175));
        registerBtn.setFont(new Font("Arial", Font.BOLD, 18));
        registerBtn.setPreferredSize(new Dimension(950, 70));
        registerBtn.setOpaque(true);
        registerBtn.setBorderPainted(false);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String pass = new String(passField.getPassword());
                
                if (email.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Παρακαλώ συμπληρώστε τα στοιχεία!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Η εγγραφή προσομοιώθηκε επιτυχώς!");
                }
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        add(registerBtn, BorderLayout.SOUTH);
    }

    private JPanel createLargeFieldPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        
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
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new CustomerRegistrationFrame().setVisible(true);
        });
    }
}