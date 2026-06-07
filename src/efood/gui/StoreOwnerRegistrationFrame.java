package efood.gui;

import efood.models.StoreOwner;
import efood.utils.DatabaseManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

// Κλάση για την εγγραφή του καταστηματάρχη
public class StoreOwnerRegistrationFrame extends JFrame {

    private JTextField nameField; 
    private JTextField emailField;
    private JPasswordField passField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField vatField; 
    private JTextField storeNameField; 

    public StoreOwnerRegistrationFrame() {
        setTitle("Εγγραφή Ιδιοκτήτη Καταστήματος");
        setSize(950, 850); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(20, 250, 20, 250));

        JLabel headerLabel = new JLabel("Εγγραφή Συνεργάτη");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        nameField = new JTextField();
        emailField = new JTextField();
        passField = new JPasswordField();
        addressField = new JTextField();
        phoneField = new JTextField();
        vatField = new JTextField();
        storeNameField = new JTextField(); 

        addLabeledInput(mainPanel, "Ονοματεπώνυμο Ιδιοκτήτη:", nameField);
        addLabeledInput(mainPanel, "Email:", emailField);
        addLabeledInput(mainPanel, "Κωδικός Πρόσβασης:", passField);
        addLabeledInput(mainPanel, "Τηλέφωνο Επικοινωνίας:", phoneField);
        addLabeledInput(mainPanel, "Επωνυμία Καταστήματος:", storeNameField); 
        addLabeledInput(mainPanel, "Διεύθυνση Καταστήματος:", addressField);
        addLabeledInput(mainPanel, "ΑΦΜ Επιχείρησης:", vatField);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton submitBtn = new JButton("ΥΠΟΒΟΛΗ");
        submitBtn.setBackground(new Color(100, 255, 100));
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setMaximumSize(new Dimension(450, 55));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword()).replace(",", "");
            String address = addressField.getText().trim().replace(",", " ");
            String phone = phoneField.getText().trim();
            String vat = vatField.getText().trim();
            String storeName = storeNameField.getText().trim().replace(",", " ");

            // τσεκάρουμε αν άφησε κάποιο πεδίο άδειο
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || phone.isEmpty() || address.isEmpty() || vat.isEmpty() || storeName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Συμπληρώστε όλα τα πεδία!", "Προσοχή", JOptionPane.WARNING_MESSAGE);
                return; 
            }
            
            if (!name.matches("^[a-zA-Zα-ωΑ-ΩάέήίόύώςΆΈΉΊΌΎΏ\\s]+$")) {
                JOptionPane.showMessageDialog(null, "Το ονοματεπώνυμο πρέπει να περιέχει μόνο γράμματα!", "Λάθος Όνομα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!(email.endsWith("@gmail.com") || email.endsWith("@hotmail.com") || email.endsWith("@yahoo.gr") || email.endsWith("@hmu.gr"))) {
                JOptionPane.showMessageDialog(null, "Μη έγκυρο email.", "Λάθος", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (phone.length() != 10 || !phone.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Λάθος τηλέφωνο.", "Λάθος", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (vat.length() != 9 || !vat.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Το ΑΦΜ πρέπει να έχει 9 αριθμούς.", "Λάθος", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // φτιάχνουμε το αντικείμενο και το σώζουμε στο αρχείο
            StoreOwner newOwner = new StoreOwner(name, email, pass, phone, address, vat, storeName);

            if (DatabaseManager.saveUser(newOwner)) {
                JOptionPane.showMessageDialog(null, "Η εγγραφή ολοκληρώθηκε!");
                dispose(); 
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Σφάλμα αποθήκευσης.", "Λάθος", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(submitBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));


        JButton backBtn = new JButton("Επιστροφή");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        
        mainPanel.add(backBtn);
        add(mainPanel, BorderLayout.CENTER);
    }

    // μέθοδος για να μη γράφουμε τα ίδια σε κάθε πεδίο (φτιάχνει label και textfield μαζί)
    private void addLabeledInput(JPanel panel, String text, JTextField field) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        
        field.setMaximumSize(new Dimension(450, 35));
        field.setPreferredSize(new Dimension(450, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(0, 10, 0, 10)));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}