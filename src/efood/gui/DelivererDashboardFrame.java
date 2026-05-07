package efood.gui;

import javax.swing.*;
import java.awt.*;

public class DelivererDashboardFrame extends JFrame {

    public DelivererDashboardFrame() {
        initComponents();
    }

    private void initComponents() {
        // Ρυθμίσεις Παραθύρου
        setTitle("Dashboard Διανομέα");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // 2. Κεντρικό Πάνελ (Main Content)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); // Χρήση null layout για ακριβή τοποθέτηση όπως στο mockup
        mainPanel.setBackground(Color.WHITE);

        // Στοιχεία Διανομέα
        JLabel nameLabel = new JLabel("Διανομέας: [Όνομα] | Κατάσταση: Ενεργός");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameLabel.setBounds(30, 20, 400, 20);
        mainPanel.add(nameLabel);

        JLabel licenseLabel = new JLabel("Αρ. Αδείας: [vehicleLicense]");
        licenseLabel.setBounds(30, 45, 400, 20);
        mainPanel.add(licenseLabel);

        // Διαθέσιμες Παραγγελίες Section
        JLabel tableTitle = new JLabel("Διαθέσιμες Παραγγελίες προς Παράδοση (JTable)");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 12));
        tableTitle.setBounds(30, 90, 400, 20);
        mainPanel.add(tableTitle);

        // Προσομοίωση JTable με JTextArea
        JTextArea tableArea = new JTextArea();
        tableArea.setText(" ID  |  Κατάστημα  |  Διεύθυνση  |  Αμοιβή\n" +
                         "------------------------------------------\n" +
                         "#501 | Pizza Fan   | Ακαδημίας 10 | 3.50€\n" +
                         "#504 | Goody's     | Σταδίου 5     | 2.80€\n" +
                         "#509 | Coffee Lab  | Πανεπιστημίου| 2.00€");
        tableArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        tableArea.setEditable(false);
        tableArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableArea.setBounds(30, 120, 420, 250);
        mainPanel.add(tableArea);

        // Κουμπί Αποδοχής
        JButton acceptBtn = new JButton("ΑΠΟΔΟΧΗ ΕΠΙΛΕΓΜΕΝΗΣ (JButton)");
        acceptBtn.setBackground(new Color(213, 232, 212)); // Απαλό πράσινο
        acceptBtn.setBounds(30, 390, 420, 50);
        mainPanel.add(acceptBtn);

        // Στοιχεία Τρέχουσας Παραγγελίας Section
        JLabel currentOrderTitle = new JLabel("Στοιχεία Τρέχουσας Παραγγελίας:");
        currentOrderTitle.setFont(new Font("Arial", Font.BOLD, 12));
        currentOrderTitle.setBounds(30, 480, 400, 20);
        mainPanel.add(currentOrderTitle);

        JPanel currentOrderPanel = new JPanel();
        currentOrderPanel.setLayout(new BoxLayout(currentOrderPanel, BoxLayout.Y_AXIS));
        currentOrderPanel.setBackground(new Color(255, 250, 205)); // Απαλό κίτρινο
        currentOrderPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        currentOrderPanel.setBounds(30, 510, 420, 90);
        
        currentOrderPanel.add(new JLabel(" Παραλαβή από: Pizza Fan"));
        currentOrderPanel.add(new JLabel(" Παράδοση σε: Ιωάννης Π. (0.5 χλμ)"));
        currentOrderPanel.add(new JLabel(" Τηλέφωνο: 698XXXXXXX"));
        mainPanel.add(currentOrderPanel);

        // Κουμπί Σήμανσης Παράδοσης
        JButton deliveredBtn = new JButton("ΣΗΜΑΝΣΗ ΩΣ ΠΑΡΑΔΟΘΗΚΕ (JButton)");
        deliveredBtn.setBackground(new Color(248, 206, 204)); // Απαλό ροζ/κόκκινο
        deliveredBtn.setBounds(30, 630, 420, 50);
        mainPanel.add(deliveredBtn);

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
            java.util.logging.Logger.getLogger(DelivererDashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new DelivererDashboardFrame().setVisible(true);
        });
    }
}