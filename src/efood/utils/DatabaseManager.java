package efood.utils;

import efood.models.*;
import java.io.*;
import java.util.*;

// Η κλάση που διαχειρίζεται τα CSV αρχεία
public class DatabaseManager {
    
    private static final String DATA_FOLDER = "data/";
    private static final String USERS_FILE = DATA_FOLDER + "users.csv";
    private static final String PRODUCTS_FILE = DATA_FOLDER + "products.csv";
    private static final String ORDERS_FILE = DATA_FOLDER + "orders.csv"; 

    // Φτιάχνει τον φάκελο data αν δεν υπάρχει
    static {
        File folder = new File("data");
        if (!folder.exists()) folder.mkdir();
    }

    // Αποθηκεύει έναν νέο χρήστη
    public static boolean saveUser(User newUser) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(USERS_FILE, true))) {
            String line = "";
            if (newUser instanceof Customer) {
                Customer c = (Customer) newUser;
                String cardsStr = String.join(";", c.getSavedCards());
                
                // Κόλπο: Αλλάζουμε τα κόμματα σε κενά για να μην χαλάσει το CSV
                String addrsStr = String.join("|", c.getSavedAddresses()).replace(",", " "); 
                
                String historyStr = "";
                if (!c.getOrderHistory().isEmpty()) {
                    historyStr = String.join("@@", c.getOrderHistory()).replace("\n", "##").replace(",", " ");
                }
                
                line = "CUSTOMER," + c.getFullName().replace(",", " ") + "," + c.getEmail() + "," + c.getPassword() + "," + c.getPhoneNumber() + "," + addrsStr + "," + c.getLoyaltyPoints() + "," + cardsStr + "," + historyStr;
            } else if (newUser instanceof StoreOwner) {
                StoreOwner o = (StoreOwner) newUser;
                line = "OWNER," + o.getFullName() + "," + o.getEmail() + "," + o.getPassword() + "," + o.getPhoneNumber() + "," + o.getAddress() + "," + o.getVatNumber() + "," + o.getStoreName() + "," + o.getApprovalStatus();
            } else if (newUser instanceof DeliveryDriver) {
                DeliveryDriver d = (DeliveryDriver) newUser;
                line = "DRIVER," + d.getFullName() + "," + d.getEmail() + "," + d.getPassword() + "," + d.getPhoneNumber() + "," + d.getAddress() + "," + d.getVehicleLicense() + "," + d.getApprovalStatus() + "," + d.getImagePath() + "," + d.getCvPath();
            }
            writer.println(line);
            return true;
        } catch (Exception e) { 
            return false; 
        }
    }

    // Διαβάζει όλους τους χρήστες από το αρχείο
    public static HashMap<String, User> loadUsers() {
        HashMap<String, User> usersMap = new HashMap<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return usersMap;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                if (p.length < 7) continue;
                
                String role = p[0], name = p[1], email = p[2], pass = p[3], phone = p[4], addr = p[5], extra = p[6];

                if (role.equals("CUSTOMER")) {
                    String[] loadedAddrs = addr.split("\\|");
                    Customer c = new Customer(name, email, pass, phone, loadedAddrs[0], Integer.parseInt(extra));
                    c.setSavedAddresses(new ArrayList<>(Arrays.asList(loadedAddrs)));
                    
                    // Φορτώνει κάρτες
                    if (p.length > 7 && !p[7].isEmpty()) {
                        String[] loadedCards = p[7].split(";");
                        c.setSavedCards(new ArrayList<>(Arrays.asList(loadedCards)));
                    }
                    // Φορτώνει ιστορικό παραγγελιών
                    if (p.length > 8 && !p[8].isEmpty()) {
                        String[] loadedHistory = p[8].split("@@");
                        ArrayList<String> histList = new ArrayList<>();
                        for (String h : loadedHistory) {
                            histList.add(h.replace("##", "\n"));
                        }
                        c.setOrderHistory(histList); 
                    }
                    usersMap.put(email, c);
                } else if (role.equals("OWNER")) {
                    StoreOwner o = new StoreOwner(name, email, pass, phone, addr, extra, p[7]);
                    if (p.length > 8) o.setApprovalStatus(StoreOwner.Status.valueOf(p[8]));
                    usersMap.put(email, o);
                } else if (role.equals("DRIVER")) {
                    DeliveryDriver d = new DeliveryDriver(name, email, pass, phone, addr, extra, (p.length > 8 ? p[8] : ""), (p.length > 9 ? p[9] : ""));
                    if (p.length > 7) d.setApprovalStatus(DeliveryDriver.Status.valueOf(p[7]));
                    usersMap.put(email, d);
                }
            }
        } catch (Exception e) {}
        return usersMap;
    }

    public static boolean updateCustomerCards(String email, ArrayList<String> newCards) {
        try {
            File file = new File(USERS_FILE);
            ArrayList<String> lines = new ArrayList<>();
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] p = line.split(",");
                    if (p.length >= 3 && p[2].equals(email) && p[0].equals("CUSTOMER")) {
                        String cardsJoined = String.join(";", newCards);
                        String p8 = (p.length > 8) ? p[8] : ""; 
                        line = "CUSTOMER," + p[1] + "," + p[2] + "," + p[3] + "," + p[4] + "," + p[5] + "," + p[6] + "," + cardsJoined + "," + p8;
                    }
                    lines.add(line);
                }
            }
            try (PrintWriter pw = new PrintWriter(new FileOutputStream(file, false))) {
                for (String l : lines) pw.println(l);
            }
            return true;
        } catch (Exception e) { return false; }
    }

    public static boolean updateCustomerAddresses(String email, ArrayList<String> newAddrs) {
        try {
            File file = new File(USERS_FILE);
            ArrayList<String> lines = new ArrayList<>();
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] p = line.split(",");
                    if (p.length >= 3 && p[2].equals(email) && p[0].equals("CUSTOMER")) {
                        String addrsJoined = String.join("|", newAddrs).replace(",", " ");
                        String p7 = (p.length > 7) ? p[7] : "";
                        String p8 = (p.length > 8) ? p[8] : ""; 
                        line = "CUSTOMER," + p[1] + "," + p[2] + "," + p[3] + "," + p[4] + "," + addrsJoined + "," + p[6] + "," + p7 + "," + p8;
                    }
                    lines.add(line);
                }
            }
            try (PrintWriter pw = new PrintWriter(new FileOutputStream(file, false))) {
                for (String l : lines) pw.println(l);
            }
            return true;
        } catch (Exception e) { return false; }
    }

    // Ενημερώνει αν κάποιος εγκρίθηκε (status)
    public static boolean updateUserStatus(String email, String newStatus) {
        try {
            File file = new File(USERS_FILE);
            ArrayList<String> lines = new ArrayList<>();
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] p = line.split(",");
                    if (p.length >= 3 && p[2].equals(email)) {
                        if (p[0].equals("DRIVER")) p[7] = newStatus;
                        else if (p[0].equals("OWNER")) p[8] = newStatus;
                        line = String.join(",", p);
                    }
                    lines.add(line);
                }
            }
            try (PrintWriter pw = new PrintWriter(new FileOutputStream(file, false))) {
                for (String l : lines) pw.println(l);
            }
            return true;
        } catch (Exception e) { return false; }
    }

    // Διαγράφει έναν χρήστη
    public static boolean deleteUser(String email) {
        try {
            ArrayList<String> lines = new ArrayList<>();
            boolean deleted = false;
            try (Scanner sc = new Scanner(new File(USERS_FILE))) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.split(",")[2].equals(email)) { deleted = true; continue; }
                    lines.add(line);
                }
            }
            if (deleted) {
                try (PrintWriter pw = new PrintWriter(new FileOutputStream(USERS_FILE, false))) {
                    for (String l : lines) pw.println(l);
                }
            }
            return deleted;
        } catch (Exception e) { return false; }
    }

    // --- ΔΙΑΧΕΙΡΙΣΗ ΠΡΟΪΟΝΤΩΝ ---
    public static boolean saveProduct(String ownerEmail, String title, double price, boolean isVegan) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(PRODUCTS_FILE, true))) {
            writer.println(ownerEmail + "," + title + "," + price + "," + isVegan);
            return true;
        } catch (Exception e) { return false; }
    }

    public static boolean updateProduct(String ownerEmail, String oldTitle, String newTitle, double newPrice, boolean newVegan) {
        File file = new File(PRODUCTS_FILE);
        if (!file.exists()) return false;
        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] p = line.split(",");
                if (p.length >= 2 && p[0].equals(ownerEmail) && p[1].equals(oldTitle)) {
                    line = ownerEmail + "," + newTitle + "," + newPrice + "," + newVegan;
                    found = true;
                }
                lines.add(line);
            }
        } catch (Exception e) { return false; }

        if (found) {
            try (PrintWriter pw = new PrintWriter(new FileOutputStream(file, false))) {
                for (String l : lines) pw.println(l);
                return true;
            } catch (Exception e) { return false; }
        }
        return false;
    }

    public static boolean deleteProduct(String ownerEmail, String title) {
        File file = new File(PRODUCTS_FILE);
        if (!file.exists()) return false;
        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] p = line.split(",");
                if (p.length >= 2 && p[0].equals(ownerEmail) && p[1].equals(title)) {
                    found = true;
                    continue; 
                }
                lines.add(line);
            }
        } catch (Exception e) { return false; }

        if (found) {
            try (PrintWriter pw = new PrintWriter(new FileOutputStream(file, false))) {
                for (String l : lines) pw.println(l);
                return true;
            } catch (Exception e) { return false; }
        }
        return false;
    }

    // --- ΔΙΑΧΕΙΡΙΣΗ ΠΑΡΑΓΓΕΛΙΩΝ ---
    public static boolean saveOrder(String id, String store, String address, String reward, String phone) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(ORDERS_FILE, true))) {
            // Βγάζουμε τα κόμματα για ασφάλεια στο CSV
            String safeStore = store.replace(",", " ");
            String safeAddress = address.replace(",", " ");
            writer.println(id + "," + safeStore + "," + safeAddress + "," + reward + "," + phone + ",PENDING");
            return true;
        } catch (Exception e) { return false; }
    }

    // Τραβάει όσες παραγγελίες δεν έχουν παραδοθεί ακόμα (PENDING)
    public static ArrayList<String[]> loadPendingOrders() {
        ArrayList<String[]> orders = new ArrayList<>();
        File file = new File(ORDERS_FILE);
        if (!file.exists()) return orders;
        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                
                String status = p[p.length - 1]; 
                if (status.equals("PENDING") && p.length >= 6) {
                    orders.add(new String[]{p[0], p[1], p[2], p[3], p[4]});
                }
            }
        } catch(Exception e) {}
        return orders;
    }

    // Αλλάζει το status της παραγγελίας σε COMPLETED
    public static boolean completeOrder(String id) {
        File file = new File(ORDERS_FILE);
        if (!file.exists()) return false;
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] p = line.split(",");
                if (p.length >= 6 && p[0].equals(id)) {
                    line = p[0] + "," + p[1] + "," + p[2] + "," + p[3] + "," + p[4] + ",COMPLETED";
                }
                lines.add(line);
            }
        } catch (Exception e) {}
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file, false))) {
            for (String l : lines) pw.println(l);
            return true;
        } catch (Exception e) { return false; }
    }

    // Βλέπει το status για να ξέρει ο πελάτης πού βρίσκεται
    public static String getOrderStatus(String id) {
        File file = new File(ORDERS_FILE);
        if (!file.exists()) return "UNKNOWN";
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                if (p.length >= 6 && p[0].equals(id)) {
                    return p[p.length - 1]; 
                }
            }
        } catch (Exception e) {}
        return "UNKNOWN";
    }
}