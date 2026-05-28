package efood.models;

import java.util.ArrayList;

// Ο Πελάτης κληρονομεί από τον User
public class Customer extends User {
    
    // Έξτρα στοιχεία πελάτη
    private ArrayList<String> orderHistory; 
    private int loyaltyPoints;
    private ArrayList<String> savedCards; 
    private ArrayList<String> savedAddresses;

    // Αρχικοποίηση
    public Customer(String fullName, String email, String password, String phoneNumber, String address, int loyaltyPoints) {
        super(fullName, email, password, phoneNumber, address);
        
        this.loyaltyPoints = loyaltyPoints;
        this.orderHistory = new ArrayList<>(); 
        this.savedCards = new ArrayList<>(); 
        this.savedAddresses = new ArrayList<>();
        // Του βάζουμε αυτόματα την πρώτη διεύθυνση
        this.savedAddresses.add(address); 
    }

    // Overriding τη μέθοδο του User
    @Override
    public String getRoleLevel() {
        return "CUSTOMER";
    }

    // Πόντοι
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }
    public void addPoints(int points) { this.loyaltyPoints += points; }

    // Ιστορικό παραγγελιών
    public ArrayList<String> getOrderHistory() { return orderHistory; }
    public void setOrderHistory(ArrayList<String> orderHistory) { this.orderHistory = orderHistory; }

    // Κάρτες
    public ArrayList<String> getSavedCards() { return savedCards; }
    public void setSavedCards(ArrayList<String> savedCards) { this.savedCards = savedCards; }
    public void addCard(String cardInfo) { this.savedCards.add(cardInfo); }
    
    // Διευθύνσεις
    public ArrayList<String> getSavedAddresses() { return savedAddresses; }
    public void setSavedAddresses(ArrayList<String> savedAddresses) { this.savedAddresses = savedAddresses; }
}