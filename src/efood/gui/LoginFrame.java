package efood.gui;

import efood.models.User;
import efood.models.Customer;
import efood.models.StoreOwner;
import efood.models.DeliveryDriver;
import efood.utils.DatabaseManager;
import efood.main.EfoodApp; // Το χρειαζόμαστε για να σώσουμε το session

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("Σύνδεση & Εγγραφή - eFood");
        setSize(950, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(40, 250, 40, 250));

        JLabel headerLabel = new JLabel("Καλωσήρθατε στο eFood");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        emailField = new JTextField();
        passField = new JPasswordField();

        addLabeledInput(mainPanel, "Ηλ. Ταχυδρομείο (Email):", emailField);
        addLabeledInput(mainPanel, "Κωδικός Πρόσβασης:", passField);

        // --- Κουμπί Σύνδεσης ---
        JButton loginBtn = new JButton("ΣΥΝΔΕΣΗ");
        loginBtn.setBackground(new Color(239, 68, 68));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setMaximumSize(new Dimension(350, 45));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        loginBtn.addActionListener(e -> performLogin());
        
        mainPanel.add(loginBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Ενότητα Εγγραφής ---
        JLabel registerAsLabel = new JLabel("Εγγραφή νέου χρήστη ως:");
        registerAsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        registerAsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(registerAsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        String[] roles = {"Πελάτης", "Διανομέας", "Κατάστημα"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setMaximumSize(new Dimension(350, 40));
        mainPanel.add(roleCombo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JButton registerBtn = new JButton("ΕΓΓΡΑΦΗ");
        registerBtn.setBackground(new Color(100, 200, 80));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        registerBtn.setMaximumSize(new Dimension(350, 45));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        registerBtn.addActionListener(e -> {
            String selected = (String) roleCombo.getSelectedItem();
            dispose();
            if (selected.equals("Πελάτης")) new CustomerRegistrationFrame().setVisible(true);
            else if (selected.equals("Κατάστημα")) new StoreOwnerRegistrationFrame().setVisible(true);
            else new DelivererRegistrationFrame().setVisible(true);
        });
        
        mainPanel.add(registerBtn);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void performLogin() {
        String email = emailField.getText().trim();
        String pass = new String(passField.getPassword());

        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Συμπληρώστε Email και Κωδικό!", "Προσοχή", JOptionPane.WARNING_MESSAGE);
            return; 
        }

        if (!(email.endsWith("@gmail.com") || email.endsWith("@hotmail.com") || 
              email.endsWith("@yahoo.gr") || email.endsWith("@outlook.com") || 
              email.endsWith("@hmu.gr") || email.equals("admin@efood.gr"))) {
            
            JOptionPane.showMessageDialog(this, "Το email πρέπει να είναι έγκυρο (π.χ. @gmail.com)!", "Λάθος Email", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- Έλεγχος για ADMIN ---
        if (email.equals("admin@efood.gr") && pass.equals("admin123")) {
            EfoodApp.saveSession(email); // ΑΠΟΘΗΚΕΥΣΗ SESSION
            dispose();
            new AdminDashboardFrame().setVisible(true);
            return;
        }

        // --- Κανονικό Login Χρηστών ---
        HashMap<String, User> allUsers = DatabaseManager.loadUsers();

        if (allUsers.containsKey(email)) {
            User user = allUsers.get(email);
            if (user.getPassword().equals(pass)) {
                EfoodApp.saveSession(email); // ΑΠΟΘΗΚΕΥΣΗ SESSION
                dispose();
                if (user instanceof Customer) new MainDashboardFrame((Customer) user).setVisible(true);
                else if (user instanceof StoreOwner) new StoreManagementFrame((StoreOwner) user).setVisible(true);
                else if (user instanceof DeliveryDriver) new DelivererDashboardFrame((DeliveryDriver) user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Λάθος Κωδικός Πρόσβασης!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ο χρήστης δεν βρέθηκε στο σύστημα!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addLabeledInput(JPanel panel, String text, JTextField field) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        field.setMaximumSize(new Dimension(450, 40));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    // --- ΠΡΟΣΤΕΘΗΚΕ Η MAIN ΓΙΑ ΝΑ ΤΡΕΧΕΙ ΑΠΕΥΘΕΙΑΣ ---
    public static void main(String[] args) {
        try { 
            // ΕΠΑΝΑΦΟΡΑ: Το CrossPlatform επιτρέπει στα δικά μας χρώματα να φαίνονται σωστά!
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); 
        } catch (Exception e) {
            System.out.println("Δεν φορτώθηκε το UI.");
        }
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}