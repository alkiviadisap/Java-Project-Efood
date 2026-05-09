package efood.gui;

import efood.models.Customer;
import efood.utils.DatabaseManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerRegistrationFrame extends JFrame {

    // 1. Dilonoume ta pedia edw panw gia na ta "vlepei" to koumpi tis eggrafis
    private JTextField emailField;
    private JPasswordField passField; // Xrisimopoioume JPasswordField gia ton kwdiko
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

        // 2. Arxikopoioume ta pedia pou dilwsame panw
        emailField = new JTextField();
        passField = new JPasswordField();
        phoneField = new JTextField();
        addressField = new JTextField();

        // 3. Ta vazoume stin othoni xrisimopoiwntas tin voithitiki methodo
        mainPanel.add(createLargeFieldPanel("Email:", emailField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Password:", passField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Phone number:", phoneField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Address:", addressField));

        mainPanel.add(Box.createVerticalGlue());

        // --- Koumpi Eggrafis ---
        JButton registerBtn = new JButton("ΕΓΓΡΑΦΗ");
        registerBtn.setBackground(new Color(255, 175, 175));
        registerBtn.setFont(new Font("Arial", Font.BOLD, 18));
        registerBtn.setPreferredSize(new Dimension(950, 70));
        registerBtn.setOpaque(true);
        registerBtn.setBorderPainted(false);

        // --- H LOGIKI TIS EGGRAFIS ---
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pairnoume ta keimena apo ta pedia
                String email = emailField.getText();
                String pass = new String(passField.getPassword());
                String phone = phoneField.getText();
                String address = addressField.getText();

                // Ena mikro check an einai adeia ta pedia (Opos grafoume stin anafora gia ta Exceptions!)
                if (email.isEmpty() || pass.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Παρακαλώ συμπληρώστε όλα τα πεδία!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return; // Stamatame tin eggrafi
                }

                // Ftiaxnoume to antikeimeno Customer
                Customer newCustomer = new Customer(email, pass, phone, address, 0);

                // To stelnoume sto DatabaseManager na swthei
                boolean success = DatabaseManager.saveUser(newCustomer);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Η εγγραφή ολοκληρώθηκε επιτυχώς!");
                    // Klinoume ayti tin othoni kai mporoume na anoiksoume to Login
                    dispose(); 
                    new LoginFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Προέκυψε σφάλμα κατά την αποθήκευση.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        add(registerBtn, BorderLayout.SOUTH);
    }

    // Voithitiki methodos. Twra dexetai to JTextField san paramtero gia na to ftiaxnei
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