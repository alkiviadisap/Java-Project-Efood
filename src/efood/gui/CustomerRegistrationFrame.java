package efood.gui;

import efood.models.Customer;
import efood.utils.DatabaseManager;
import efood.main.EfoodApp; 

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerRegistrationFrame extends JFrame {

    private JTextField nameField; 
    private JTextField emailField;
    private JPasswordField passField; 
    private JTextField phoneField;
    private JTextField addressField;

    public CustomerRegistrationFrame() {
        setTitle("Φόρμα Εγγραφής Πελάτη");
        setSize(950, 800); 
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

        nameField = new JTextField();
        emailField = new JTextField();
        passField = new JPasswordField();
        phoneField = new JTextField();
        addressField = new JTextField();

        mainPanel.add(createLargeFieldPanel("Ονοματεπώνυμο:", nameField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        mainPanel.add(createLargeFieldPanel("Email:", emailField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Κωδικός Πρόσβασης:", passField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Τηλέφωνο Επικοινωνίας:", phoneField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainPanel.add(createLargeFieldPanel("Διεύθυνση Παράδοσης:", addressField));

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
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String pass = new String(passField.getPassword());
                String phone = phoneField.getText().trim();
                String address = addressField.getText().trim();

                // basikoi elegxoi
                if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Συμπλήρωσε όλα τα πεδία!", "Προσοχή", JOptionPane.WARNING_MESSAGE);
                    return; 
                }

                // elegxos mono gia grammata kai kena sto onoma
                if (!name.matches("^[a-zA-Zα-ωΑ-ΩάέήίόύώςΆΈΉΊΌΎΏ\\s]+$")) {
                    JOptionPane.showMessageDialog(null, "Το ονοματεπώνυμο πρέπει να περιέχει μόνο γράμματα!", "Λάθος Όνομα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!(email.endsWith("@gmail.com") || email.endsWith("@hotmail.com") || 
                      email.endsWith("@yahoo.gr") || email.endsWith("@outlook.com") || 
                      email.endsWith("@hmu.gr"))) {
                    JOptionPane.showMessageDialog(null, "Μη έγκυρο email.", "Λάθος", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (phone.length() != 10 || !phone.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Το τηλέφωνο πρέπει να έχει 10 ψηφία.", "Λάθος", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Customer newCustomer = new Customer(name, email, pass, phone, address, 0);
                boolean success = DatabaseManager.saveUser(newCustomer);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Επιτυχής εγγραφή!");
                    EfoodApp.saveSession(email);
                    dispose(); 
                    new MainDashboardFrame(newCustomer).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Σφάλμα αποθήκευσης.", "Λάθος", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backBtn = new JButton("Επιστροφή");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(backBtn);

        add(mainPanel, BorderLayout.CENTER);
        add(registerBtn, BorderLayout.SOUTH);
    }

    // boithitiki methodos gia ta textfields
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
}