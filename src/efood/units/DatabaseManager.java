package efood.utils;

import efood.models.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.HashMap;

public class DatabaseManager {
    
    // To onoma tou arxeiou mas
    private static final String USERS_FILE = "users.csv";

    // Methodos gia na swzoume enan neo xristi sto arxeio
    public static boolean saveUser(User newUser) {
        try {
            // To 'true' simainei append (na mpei sto telos tou arxeiou, oxi na svisel ta palia)
            FileOutputStream fos = new FileOutputStream(USERS_FILE, true);
            PrintWriter writer = new PrintWriter(fos);
            
            // Ftiaxnoume ti grammi pou tha graftei sto csv analoga me to rolo
            String lineToSave = "";
            
            if (newUser instanceof Customer) {
                Customer c = (Customer) newUser;
                // Morfi: ROLE, email, password, phone, address, extra (loyalty)
                lineToSave = c.getRoleLevel() + "," + c.getEmail() + "," + c.getPassword() + "," 
                           + c.getPhoneNumber() + "," + c.getAddress() + "," + c.getLoyaltyPoints();
            } 
            else if (newUser instanceof StoreOwner) {
                StoreOwner o = (StoreOwner) newUser;
                // Morfi: ROLE, email, password, phone, address, extra (vat)
                lineToSave = o.getRoleLevel() + "," + o.getEmail() + "," + o.getPassword() + "," 
                           + o.getPhoneNumber() + "," + o.getAddress() + "," + o.getVatNumber();
            } 
            else if (newUser instanceof DeliveryDriver) {
                DeliveryDriver d = (DeliveryDriver) newUser;
                // Morfi: ROLE, email, password, phone, address, extra (license)
                lineToSave = d.getRoleLevel() + "," + d.getEmail() + "," + d.getPassword() + "," 
                           + d.getPhoneNumber() + "," + d.getAddress() + "," + d.getVehicleLicense();
            }
            
            // Grafoume ti grammi sto arxeio
            writer.println(lineToSave);
            
            // Kleinoume ta streams gia na swthei to arxeio
            writer.flush();
            writer.close();
            fos.close();
            
            return true; // Ola pigan kala
            
        } catch (Exception e) {
            System.out.println("Lathos kata tin apothikeusi tou xristi: " + e.getMessage());
            return false;
        }
    }

    // Methodos gia na fortwnoume olous tous xristes apo to arxeio (gia to Login)
    public static HashMap<String, User> loadUsers() {
        // To HashMap pou tha epistrepsoume (kleidi to email, timi to antikeimeno User)
        HashMap<String, User> usersMap = new HashMap<>();
        
        try {
            File file = new File(USERS_FILE);
            // An den uparxei to arxeio (prwth fora pou trexei), epistrefoume adeio map
            if (!file.exists()) {
                return usersMap;
            }
            
            Scanner scanner = new Scanner(file);
            
            // Diavazoume grammi-grammi
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Xwrizoume ti grammi me vasi to komma
                String[] parts = line.split(",");
                
                // An i grammi den exei toulaxiston 6 stoixeia, tin prospername (einai lathos)
                if (parts.length < 6) continue;
                
                String role = parts[0];
                String email = parts[1];
                String password = parts[2];
                String phone = parts[3];
                String address = parts[4];
                String extraInfo = parts[5]; // MPorei na einai loyalty, vat i license
                
                // Ftiaxnoume to swsto antikeimeno analoga me to rolo
                if (role.equals("CUSTOMER")) {
                    int points = Integer.parseInt(extraInfo);
                    Customer c = new Customer(email, password, phone, address, points);
                    usersMap.put(email, c);
                } 
                else if (role.equals("OWNER")) {
                    StoreOwner o = new StoreOwner(email, password, phone, address, extraInfo);
                    usersMap.put(email, o);
                } 
                else if (role.equals("DRIVER")) {
                    DeliveryDriver d = new DeliveryDriver(email, password, phone, address, extraInfo);
                    usersMap.put(email, d);
                }
            }
            scanner.close();
            
        } catch (Exception e) {
            System.out.println("Lathos kata to diavasma twn xristwn: " + e.getMessage());
        }
        
        return usersMap;
    }
}