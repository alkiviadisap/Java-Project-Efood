package efood.gui2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StoreManagementFrame extends JFrame {

    public StoreManagementFrame() {
        // Ρυθμίσεις Παραθύρου - Laptop Layout
        setTitle("Store Management");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Κύριο Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(30, 80, 30, 80));

        // --- Header Section ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel storeTitle = new JLabel("Store Name & Vat (labels)");
        storeTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton backBtn = new JButton("Go Back");
        backBtn.setBackground(new Color(239, 68, 68)); // Κόκκινο
        backBtn.setForeground(Color.WHITE);
        backBtn.setPreferredSize(new Dimension(120, 35));

        headerPanel.add(storeTitle, BorderLayout.WEST);
        headerPanel.add(backBtn, BorderLayout.EAST);
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Table Section (Inventory) ---
        String[] columns = {"Product ID", "Product Name", "Price"};
        Object[][] data = {
            {"#001", "Cheeseburger", "5.50€"},
            {"#002", "Pizza Margherita", "8.00€"},
            {"", "", ""}, {"", "", ""}, {"", "", ""} // Κενές γραμμές όπως στο mockup
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable productTable = new JTable(model);
        productTable.setRowHeight(30);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // --- Management Controls Panel ---
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        controlsPanel.setOpaque(false);

        JButton uploadBtn = new JButton("Upload Image");
        uploadBtn.setBackground(new Color(192, 132, 252)); // Μοβ/Λιλά
        uploadBtn.setPreferredSize(new Dimension(180, 40));
        
        JCheckBox sponsorCheck = new JCheckBox("Sponsor Store");
        sponsorCheck.setFont(new Font("Arial", Font.PLAIN, 14));
        sponsorCheck.setOpaque(false);

        controlsPanel.add(uploadBtn);
        controlsPanel.add(sponsorCheck);
        mainPanel.add(controlsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Settings Section ---
        JButton settingsBtn = new JButton("My Settings");
        settingsBtn.setMaximumSize(new Dimension(400, 45));
        settingsBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(settingsBtn);
        
        mainPanel.add(Box.createVerticalGlue());

        // --- Footer Action ---
        JButton saveCsvBtn = new JButton("SAVE ALL TO CSV");
        saveCsvBtn.setBackground(new Color(248, 180, 180)); // Απαλό ροζ/κόκκινο
        saveCsvBtn.setFont(new Font("Arial", Font.BOLD, 16));
        saveCsvBtn.setMaximumSize(new Dimension(500, 60));
        saveCsvBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(saveCsvBtn);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } 
        catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new StoreManagementFrame().setVisible(true));
    }
}