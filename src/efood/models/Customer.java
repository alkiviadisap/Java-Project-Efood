package efood.models;

import java.util.ArrayList;

public class Customer extends User {
    
    // extra pedia gia ton pelati
    private ArrayList<String> orderHistory; // lysta me tis paragelies tou
    private int loyaltyPoints;
    private ArrayList<String> savedCards; // ΝΕΟ: Η λίστα με τις αποθηκευμένες κάρτες!

    // constructor
    public Customer(String fullName, String email, String password, String phoneNumber, String address, int loyaltyPoints) {
        // stelnoume kai to fullName sti mana
        super(fullName, email, password, phoneNumber, address);
        
        this.loyaltyPoints = loyaltyPoints;
        this.orderHistory = new ArrayList<>(); // arxikopoioume ti lista adeia
        this.savedCards = new ArrayList<>(); // arxikopoioume adeia lista gia tis kartes
    }

    // upervasi ths abstract methodou ths mamas
    @Override
    public String getRoleLevel() {
        return "CUSTOMER";
    }

    // getters / setters
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }

    public ArrayList<String> getOrderHistory() { return orderHistory; }

    // methodos gia na tou dinoume pontous apo tin pinata
    public void addPoints(int points) { this.loyaltyPoints += points; }

    // --- ΝΕΟ: Getters & Setters για τις κάρτες ---
    public ArrayList<String> getSavedCards() { return savedCards; }
    public void setSavedCards(ArrayList<String> savedCards) { this.savedCards = savedCards; }
    public void addCard(String cardInfo) { this.savedCards.add(cardInfo); }
}