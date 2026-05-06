package efood.gui;

import javax.swing.*;
import java.awt.*;

public class DelivererRegistrationFrame extends JFrame {

    public DelivererRegistrationFrame() {
        initComponents();
    }

    private void initComponents() {
        // Ρυθμίσεις Παραθύρου
        setTitle("Αίτηση Νέου Διανομέα");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 650); // Μειωμένο ύψος αφού αφαιρέθηκε περιέχομενο
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2. Κεντρικό Πάνελ
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(new JLabel("Ονοματεπώνυμο (JTextField)"));
        mainPanel.add(new JTextField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        mainPanel.add(new JLabel("Email (JTextField)"));
        mainPanel.add(new JTextField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        mainPanel.add(new JLabel("Αριθμός Διπλώματος (vehicleLicense)"));
        mainPanel.add(new JTextField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        mainPanel.add(new JLabel("Τύπος Οχήματος (JComboBox)"));
        String[] vehicles = {"Μοτοσυκλέτα", "Αυτοκίνητο", "Ποδήλατο", "Ηλεκτρικό Πατίνι"};
        JComboBox<String> vehicleCombo = new JComboBox<>(vehicles);
        mainPanel.add(vehicleCombo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton uploadBtn = new JButton("Ανέβασμα Φωτογραφίας Διπλώματος");
        uploadBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        mainPanel.add(uploadBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // 3. Περιοχή CAPTCHA & Κωδικού
        JPanel captchaPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        captchaPanel.setBackground(Color.WHITE);
        
        JLabel captchaBox = new JLabel("[CAPTCHA]", SwingConstants.CENTER);
        captchaBox.setOpaque(true);
        captchaBox.setBackground(new Color(250, 210, 210));
        captchaBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JTextField codeField = new JTextField("Κωδικός");
        
        captchaPanel.add(captchaBox);
        captchaPanel.add(codeField);
        captchaPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        mainPanel.add(captchaPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // 4. Κουμπί Υποβολής
        JButton submitBtn = new JButton("ΥΠΟΒΟΛΗ ΑΙΤΗΣΗΣ (JButton)");
        submitBtn.setBackground(new Color(30, 80, 255));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(submitBtn);

        // Στοίχιση των labels
        for (Component c : mainPanel.getComponents()) {
            if (c instanceof JLabel) {
                ((JLabel) c).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }

        // ΠΡΟΣΟΧΗ: Προσθήκη απευθείας του mainPanel χωρίς JScrollPane
        add(mainPanel, BorderLayout.CENTER);
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
            java.util.logging.Logger.getLogger(DelivererRegistrationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new DelivererRegistrationFrame().setVisible(true);
        });
    }
}