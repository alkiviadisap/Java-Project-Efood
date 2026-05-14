package efood.models;

import java.util.ArrayList;

public class Customer extends User {
    
    private ArrayList<String> orderHistory; 
    private int loyaltyPoints;
    private ArrayList<String> savedCards; 

    public Customer(String fullName, String email, String password, String phoneNumber, String address, int loyaltyPoints) {
        super(fullName, email, password, phoneNumber, address);
        
        this.loyaltyPoints = loyaltyPoints;
        this.orderHistory = new ArrayList<>(); 
        this.savedCards = new ArrayList<>(); 
    }

    @Override
    public String getRoleLevel() {
        return "CUSTOMER";
    }

    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }

    public ArrayList<String> getOrderHistory() { return orderHistory; }

    public void addPoints(int points) { this.loyaltyPoints += points; }

    public ArrayList<String> getSavedCards() { return savedCards; }
    public void setSavedCards(ArrayList<String> savedCards) { this.savedCards = savedCards; }
    public void addCard(String cardInfo) { this.savedCards.add(cardInfo); }
}