package efood.gui;

import javax.swing.*;
import java.awt.*;

public class CustomerRegistrationFrame extends JFrame {

    public CustomerRegistrationFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Φόρμα Εγγραφής Πελάτη");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        mainPanel.add(new JLabel("Email (JTextField)"));
        mainPanel.add(new JTextField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        mainPanel.add(new JLabel("Κωδικός (JPasswordField)"));
        mainPanel.add(new JPasswordField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        mainPanel.add(new JLabel("Τηλέφωνο (JTextField)"));
        mainPanel.add(new JTextField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        mainPanel.add(new JLabel("Διεύθυνση (JTextField)"));
        mainPanel.add(new JTextField());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel capPanel = new JPanel(new FlowLayout());
        capPanel.add(new JLabel("[CAPTCHA]"));
        capPanel.add(new JTextField(10));
        mainPanel.add(capPanel);

        JButton btn = new JButton("ΟΛΟΚΛΗΡΩΣΗ ΕΓΓΡΑΦΗΣ (JButton)");
        btn.setBackground(Color.RED);
        btn.setForeground(Color.WHITE);
        btn.setMaximumSize(new Dimension(300, 40));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(btn);

        for (Component c : mainPanel.getComponents()) {
            if (c instanceof JComponent) ((JComponent) c).setAlignmentX(Component.CENTER_ALIGNMENT);
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
            java.util.logging.Logger.getLogger(CustomerRegistrationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new CustomerRegistrationFrame().setVisible(true);
        });
    }
}