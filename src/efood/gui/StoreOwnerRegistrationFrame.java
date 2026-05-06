package efood.gui;

import javax.swing.*;
import java.awt.*;

public class StoreOwnerRegistrationFrame extends JFrame {

    public StoreOwnerRegistrationFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Φόρμα Εγγραφής Καταστηματάρχη");
        setSize(450, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        mainPanel.add(new JLabel("Email Επιχείρησης (JTextField)"));
        mainPanel.add(new JTextField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(new JLabel("Κωδικός Πρόσβασης (JPasswordField)"));
        mainPanel.add(new JPasswordField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(new JLabel("Αριθμός ΑΦΜ (vatNumber JTextField)"));
        mainPanel.add(new JTextField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(new JLabel("Τηλέφωνο Επικοινωνίας (JTextField)"));
        mainPanel.add(new JTextField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton submit = new JButton("AIΤΗΣΗ ΕΓΓΡΑΦΗΣ ΚΑΤΑΣΤΗΜΑΤΟΣ");
        submit.setBackground(new Color(213, 232, 212));
        submit.setMaximumSize(new Dimension(300, 40));
        mainPanel.add(submit);

        for (Component c : mainPanel.getComponents()) {
            if (c instanceof JComponent) {
                ((JComponent) c).setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }
        add(mainPanel);
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
            java.util.logging.Logger.getLogger(StoreOwnerRegistrationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new StoreOwnerRegistrationFrame().setVisible(true);
        });
    }
}