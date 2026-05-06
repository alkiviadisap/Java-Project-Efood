package efood.gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginFrame.class.getName());

    public LoginFrame() {
        initComponents();
        
        // --- ΤΟ ΚΟΛΠΟ: Καλούμε τον δικό μας κώδικα αμέσως μετά! ---
        buildCustomUI();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        // Το NetBeans κλειδώνει αυτό το σημείο, οπότε το αφήνουμε άδειο!
    }// </editor-fold>                        

    // --- Ο ΔΙΚΟΣ ΜΑΣ ΚΩΔΙΚΑΣ ΠΟΥ ΦΤΙΑΧΝΕΙ ΑΚΡΙΒΩΣ ΤΗΝ ΕΙΚΟΝΑ ---
    private void buildCustomUI() {
        // 1. Καθαρίζουμε τον καμβά
        this.getContentPane().removeAll();
        this.setTitle("Παράθυρο Σύνδεσης & Εγγραφής");
        this.setSize(450, 600);
        this.setLocationRelativeTo(null); // Άνοιγμα στο κέντρο της οθόνης
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. Φτιάχνουμε το κεντρικό Panel που θα μπαίνουν όλα κάθετα
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Μέθοδος-βοηθός για να στοιχίζονται όλα στο κέντρο
        class UIHelper {
            void addCentered(JComponent c) {
                c.setAlignmentX(Component.CENTER_ALIGNMENT);
                mainPanel.add(c);
            }
        }
        UIHelper helper = new UIHelper();

        // 3. Email
        helper.addCentered(new JLabel("Email (JTextField)"));
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(300, 30));
        helper.addCentered(emailField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // 4. Κωδικός
        helper.addCentered(new JLabel("Κωδικός Πρόσβασης (JPasswordField)"));
        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(300, 30));
        helper.addCentered(passField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // 5. Εικόνα CAPTCHA
        JLabel captchaLabel = new JLabel("[Εικόνα CAPTCHA: JLabel]", SwingConstants.CENTER);
        captchaLabel.setOpaque(true);
        captchaLabel.setBackground(new Color(200, 220, 255)); // Γαλάζιο χρώμα
        captchaLabel.setMaximumSize(new Dimension(200, 50));
        captchaLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        helper.addCentered(captchaLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // 6. Εισαγωγή CAPTCHA
        helper.addCentered(new JLabel("Εισάγετε Κωδικό (JTextField)"));
        JTextField captchaField = new JTextField();
        captchaField.setMaximumSize(new Dimension(200, 30));
        helper.addCentered(captchaField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // 7. Κουμπί ΣΥΝΔΕΣΗ
        JButton loginBtn = new JButton("ΣΥΝΔΕΣΗ (JButton)");
        loginBtn.setBackground(new Color(255, 200, 200)); // Κοκκινωπό
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setMaximumSize(new Dimension(300, 40));
        helper.addCentered(loginBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

// 8. Επιλογή Ρόλου
        helper.addCentered(new JLabel("Εγγραφή ως: (Επιλογή Ρόλου)"));
        // Τώρα είναι 3 ξεχωριστές επιλογές!
        String[] roles = {"Επιλέξτε Ρόλο...", "Πελάτης", "Ιδιοκτήτης Καταστήματος", "Διανομέας"}; 
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setMaximumSize(new Dimension(300, 30));
        helper.addCentered(roleCombo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // 9. Κουμπί ΕΓΓΡΑΦΗ
        JButton registerBtn = new JButton("ΕΓΓΡΑΦΗ (JButton)");
        registerBtn.setBackground(new Color(210, 240, 210)); // Πρασινωπό
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        registerBtn.setMaximumSize(new Dimension(300, 40));
        helper.addCentered(registerBtn);

        // Προσθήκη του panel στο παράθυρο
        this.add(mainPanel);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}