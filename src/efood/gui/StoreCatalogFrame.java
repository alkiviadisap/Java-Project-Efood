package efood.gui;

import efood.models.Customer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class StoreCatalogFrame extends JFrame {

    private Customer currentCustomer;
    private ArrayList<String[]> myCart; 
    private DefaultTableModel menuModel;
    private JTable menuTable;
    private JButton nextBtn;

    public StoreCatalogFrame(Customer customer, String storeName, String storeEmail, ArrayList<String[]> cart) {
        this.currentCustomer = customer;
        this.myCart = cart; 
        
        setTitle("Κατάλογος: " + storeName);
        setSize(900, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Κατάστημα: " + storeName, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        String[] cols = {"Προϊόν", "Τιμή (€)", "Vegan"};
        menuModel = new DefaultTableModel(null, cols) { 
            @Override public boolean isCellEditable(int r, int c) { return false; } 
        };
        menuTable = new JTable(menuModel);
        menuTable.setRowHeight(35);
        menuTable.setFont(new Font("Arial", Font.PLAIN, 14));
        
        loadMenu(storeEmail);
        add(new JScrollPane(menuTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        
        JButton backBtn = new JButton("← Πίσω στα Καταστήματα");
        backBtn.setBackground(new Color(255, 180, 180));
        backBtn.addActionListener(e -> { 
            dispose(); 
            new MainDashboardFrame(currentCustomer, myCart).setVisible(true); 
        });

        JButton addBtn = new JButton("➕ Προσθήκη στο Καλάθι");
        addBtn.setBackground(new Color(150, 200, 255));
        addBtn.addActionListener(e -> {
            int row = menuTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Επίλεξε ένα προϊόν!"); return;
            }
            String name = (String) menuModel.getValueAt(row, 0);
            String price = (String) menuModel.getValueAt(row, 1);
            
            myCart.add(new String[]{storeName + " - " + name, price});
            updateNextButton(); 
            JOptionPane.showMessageDialog(this, name + " προστέθηκε στο καλάθι!");
        });

        nextBtn = new JButton();
        nextBtn.setBackground(new Color(150, 255, 150));
        updateNextButton(); 
        
        nextBtn.addActionListener(e -> {
            if (myCart.isEmpty()) { 
                JOptionPane.showMessageDialog(this, "Το καλάθι είναι άδειο!"); return; 
            }
            dispose();
            new CartCheckoutFrame(currentCustomer, myCart).setVisible(true);
        });

        bottomPanel.add(backBtn);
        bottomPanel.add(addBtn);
        bottomPanel.add(nextBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateNextButton() {
        double total = 0;
        for (String[] item : myCart) total += Double.parseDouble(item[1]);
        nextBtn.setText("🛒 Ταμείο (" + myCart.size() + " | " + String.format("%.2f", total) + "€) →");
    }

    private void loadMenu(String email) {
        try (BufferedReader br = new BufferedReader(new FileReader("data/products.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 4 && p[0].equals(email)) {
                    menuModel.addRow(new Object[]{p[1], p[2], p[3].equals("true") ? "🌱 Ναι" : "Όχι"});
                }
            }
        } catch (Exception e) {}
    }
}