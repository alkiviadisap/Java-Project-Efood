package efood.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DelivererDashboardFrame extends JFrame {

    public DelivererDashboardFrame() {
        // Ρυθμίσεις Παραθύρου - Extra Wide Laptop Layout
        setTitle("Dashboard Διανομέα");
        setSize(950, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Κύριο Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // Πολύ μεγάλα περιθώρια δεξιά-αριστερά (180px) για να παραμένει η πληροφορία κεντραρισμένη
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 180, 20, 180));
        mainPanel.setBackground(new Color(248, 248, 248));

        // 1. Στοιχεία Διανομέα (Header)
        JLabel nameLabel = new JLabel("Διανομέας: [Όνομα] | Κατάσταση: Ενεργός");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(nameLabel);

        JLabel licenseLabel = new JLabel("Αρ. Αδείας: [vehicleLicense]");
        licenseLabel.setForeground(Color.DARK_GRAY);
        licenseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(licenseLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        // 2. Διαθέσιμες Παραγγελίες Section
        JLabel tableTitle = new JLabel("Διαθέσιμες Παραγγελίες προς Παράδοση:");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 13));
        tableTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(tableTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] columnNames = {"ID", "Κατάστημα", "Διεύθυνση", "Αμοιβή"};
        Object[][] data = {
            {"#501", "Pizza Fan", "Ακαδημίας 10", "3.50€"},
            {"#504", "Goody's", "Σταδίου 5", "2.80€"},
            {"#509", "Coffee Lab", "Πανεπιστημίου", "2.00€"}
        };

        JTable ordersTable = new JTable(new DefaultTableModel(data, columnNames));
        ordersTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        // Ο πίνακας τώρα απλώνεται περισσότερο
        scrollPane.setPreferredSize(new Dimension(590, 180)); 
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(scrollPane);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        JButton acceptBtn = new JButton("ΑΠΟΔΟΧΗ ΕΠΙΛΕΓΜΕΝΗΣ ΠΑΡΑΓΓΕΛΙΑΣ");
        acceptBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        acceptBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        acceptBtn.setFocusPainted(false);
        mainPanel.add(acceptBtn);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // 3. Στοιχεία Τρέχουσας Παραγγελίας (Highlight Box)
        JLabel currentOrderTitle = new JLabel("Στοιχεία Τρέχουσας Παραγγελίας:");
        currentOrderTitle.setFont(new Font("Arial", Font.BOLD, 13));
        currentOrderTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(currentOrderTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel currentOrderPanel = new JPanel();
        currentOrderPanel.setLayout(new BoxLayout(currentOrderPanel, BoxLayout.Y_AXIS));
        currentOrderPanel.setBackground(new Color(255, 252, 220)); // Απαλό κίτρινο
        currentOrderPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 180, 0), 1),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)));
        currentOrderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        currentOrderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        currentOrderPanel.add(new JLabel("📍 Παραλαβή: Pizza Fan"));
        currentOrderPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        currentOrderPanel.add(new JLabel("🏠 Παράδοση: Ιωάννης Π. (0.5 χλμ)"));
        currentOrderPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        currentOrderPanel.add(new JLabel("📞 Τηλέφωνο: 698XXXXXXX"));
        mainPanel.add(currentOrderPanel);

        // Σπρώχνει τα πάντα προς τα πάνω για να μείνει το κουμπί στο τέλος
        mainPanel.add(Box.createVerticalGlue());

        // 4. Κεντρικό Κουμπί Ολοκλήρωσης (Σομόν - Bottom)
        JButton deliveredBtn = new JButton("ΣΗΜΑΝΣΗ ΩΣ ΠΑΡΑΔΟΘΗΚΕ");
        deliveredBtn.setBackground(new Color(255, 175, 175));
        deliveredBtn.setPreferredSize(new Dimension(950, 60)); // Πιο παχύ κουμπί
        deliveredBtn.setFont(new Font("Arial", Font.BOLD, 15));
        deliveredBtn.setOpaque(true);
        deliveredBtn.setBorderPainted(false);

        add(mainPanel, BorderLayout.CENTER);
        add(deliveredBtn, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        // Look and Feel για να δουλεύουν σωστά τα χρώματα στα κουμπιά
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new DelivererDashboardFrame().setVisible(true);
        });
    }
}