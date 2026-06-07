package efood.gui;

import efood.models.User;
import efood.models.Customer;
import efood.models.StoreOwner;
import efood.models.DeliveryDriver;
import efood.utils.DatabaseManager;
import efood.main.EfoodApp; 

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
        // βάζουμε μεγάλα περιθώρια δεξιά-αριστερά για να μην απλώνουν τα πεδία σε όλο το παράθυρο
        mainPanel.setBorder(new EmptyBorder(40, 250, 40, 250));

        JLabel headerLabel = new JLabel("Καλωσήρθατε στο eFood");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        emailField = new JTextField();
        passField = new JPasswordField();

        addLabeledInput(mainPanel, "Email:", emailField);
        addLabeledInput(mainPanel, "Κωδικός Πρόσβασης:", passField);

        JButton loginBtn = new JButton("ΣΥΝΔΕΣΗ");
        loginBtn.setBackground(new Color(239, 68, 68));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setMaximumSize(new Dimension(350, 45));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        loginBtn.addActionListener(e -> performLogin());
        
        mainPanel.add(loginBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

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
            // ανάλογα τι διάλεξε στο dropdown, του ανοίγει την αντίστοιχη φόρμα εγγραφής
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

        if (email.equals("admin@efood.gr") && pass.equals("admin123")) {
            EfoodApp.saveSession(email); 
            dispose();
            new AdminDashboardFrame().setVisible(true);
            return;
        }

        // φορτώνουμε όλους τους χρήστες στο HashMap για να ψάξουμε αν υπάρχει το email
        HashMap<String, User> allUsers = DatabaseManager.loadUsers();

        if (allUsers.containsKey(email)) {
            User user = allUsers.get(email);
            if (user.getPassword().equals(pass)) {
                EfoodApp.saveSession(email); 
                dispose();
                
                // downcasting: βλέπουμε τι ρόλο έχει ο χρήστης για να του ανοίξουμε το σωστό παράθυρο
                if (user instanceof Customer) new MainDashboardFrame((Customer) user).setVisible(true);
                else if (user instanceof StoreOwner) new StoreManagementFrame((StoreOwner) user).setVisible(true);
                else if (user instanceof DeliveryDriver) new DelivererDashboardFrame((DeliveryDriver) user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Λάθος Κωδικός Πρόσβασης!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ο χρήστης δεν βρέθηκε.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    // βοηθητική μέθοδος για να μη γράφουμε τα ίδια JLabel και JTextField ξανά και ξανά
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

    public static void main(String[] args) {
        // ΕΝΕΡΓΟΠΟΙΗΣΗ ΤΟΥ NIMBUS THEME
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}