package efood.main;

import efood.gui.*;
import efood.models.*;
import efood.utils.DatabaseManager;

import javax.swing.UIManager;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.HashMap;

public class EfoodApp {

    public static void main(String[] args) {
        
        // ΕΝΕΡΓΟΠΟΙΗΣΗ ΤΟΥ NIMBUS THEME (ΤΕΛΕΙΑ ΧΡΩΜΑΤΑ ΚΑΙ ΣΧΗΜΑΤΑ ΣΕ WINDOWS ΚΑΙ MAC)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Το Nimbus Theme δεν φορτώθηκε.");
        }
        
        File sessionFile = new File("data/session.txt");
        if (sessionFile.exists()) {
            try (Scanner sc = new Scanner(sessionFile)) {
                if (sc.hasNextLine()) {
                    String savedEmail = sc.nextLine().trim();
                    
                    if (savedEmail.equals("admin@efood.gr")) {
                        new AdminDashboardFrame().setVisible(true);
                        return; 
                    }
                    
                    HashMap<String, User> users = DatabaseManager.loadUsers();
                    if (users.containsKey(savedEmail)) {
                        User user = users.get(savedEmail);
                        if (user instanceof Customer) new MainDashboardFrame((Customer) user).setVisible(true);
                        else if (user instanceof StoreOwner) new StoreManagementFrame((StoreOwner) user).setVisible(true);
                        else if (user instanceof DeliveryDriver) new DelivererDashboardFrame((DeliveryDriver) user).setVisible(true);
                        return; 
                    }
                }
            } catch (Exception e) {
                System.out.println("Λάθος στο session.");
            }
        }
        
        LoginFrame arxiki = new LoginFrame();
        arxiki.setVisible(true);
    }
    
    public static void saveSession(String email) {
        try (PrintWriter pw = new PrintWriter(new File("data/session.txt"))) {
            pw.println(email);
        } catch (Exception e) {
            System.out.println("Δεν σώθηκε το session.");
        }
    }
    
    public static void clearSession() {
        File f = new File("data/session.txt");
        if (f.exists()) {
            f.delete();
        }
    }
}