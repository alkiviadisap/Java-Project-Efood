package efood.gui;

import javax.swing.*;
import java.awt.*;

public class StoreManagementFrame extends JFrame {

    public StoreManagementFrame() {
        initComponents();
    }

    private void initComponents() {
        // Ρυθμίσεις Παραθύρου
        setTitle("Διαχείριση Καταστήματος");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2. Κεντρικό Πάνελ (Main Content)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // null layout για ταύτιση με το mockup
        mainPanel.setBackground(Color.WHITE);

        // Store Name & VAT Labels
        JLabel infoLabel = new JLabel("Store Name & VAT (Labels)");
        infoLabel.setBounds(30, 20, 300, 20);
        mainPanel.add(infoLabel);

        // Current Menu (JTable or List Placeholder)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tablePanel.setBackground(Color.WHITE);
        JLabel tablePlaceholder = new JLabel("Current Menu (JTable or List)", SwingConstants.CENTER);
        tablePanel.add(tablePlaceholder, BorderLayout.CENTER);
        tablePanel.setBounds(30, 50, 420, 250);
        mainPanel.add(tablePanel);

        // Buttons: Upload Image & Update Price
        JButton uploadBtn = new JButton("Upload Image (JFileChooser)");
        uploadBtn.setBackground(new Color(210, 200, 230)); // Απαλό μωβ
        uploadBtn.setBounds(30, 320, 200, 40);
        mainPanel.add(uploadBtn);

        JButton updateBtn = new JButton("Update Price (JButton)");
        updateBtn.setBackground(new Color(213, 232, 212)); // Απαλό πράσινο
        updateBtn.setBounds(250, 320, 200, 40);
        mainPanel.add(updateBtn);

        // Sponsor Store CheckBox
        JCheckBox sponsorCheck = new JCheckBox("Sponsor Store (JCheckBox)");
        sponsorCheck.setBackground(Color.WHITE);
        sponsorCheck.setBounds(30, 380, 250, 30);
        mainPanel.add(sponsorCheck);

        // SAVE ALL TO CSV Button (Κάτω μέρος)
        JButton saveBtn = new JButton("SAVE ALL TO CSV (JButton)");
        saveBtn.setBackground(new Color(248, 206, 204)); // Απαλό ροζ/κόκκινο
        saveBtn.setFont(new Font("Arial", Font.BOLD, 13));
        saveBtn.setBounds(30, 550, 420, 50);
        mainPanel.add(saveBtn);

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
            java.util.logging.Logger.getLogger(StoreManagementFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new StoreManagementFrame().setVisible(true);
        });
    }
}