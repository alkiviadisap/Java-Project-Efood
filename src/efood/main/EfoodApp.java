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
        
        // rithmisi tou UI gia na exoun xrwmata ta kourmpia
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No UI loaded.");
        }
        
        // elegxos an uparxei idi kapoio active session (auto-login)
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
                System.out.println("Session error.");
            }
        }
        
        LoginFrame arxiki = new LoginFrame();
        arxiki.setVisible(true);
    }
    
    // voithitikes methodoi gia log in / log out
    public static void saveSession(String email) {
        try (PrintWriter pw = new PrintWriter(new File("data/session.txt"))) {
            pw.println(email);
        } catch (Exception e) {
            System.out.println("Save session error.");
        }
    }
    
    public static void clearSession() {
        File f = new File("data/session.txt");
        if (f.exists()) {
            f.delete();
        }
    }
}