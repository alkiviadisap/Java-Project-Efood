package efood.models;

import java.util.ArrayList;

public class Customer extends User {
    
    // extra pedia gia ton pelati
    private ArrayList<String> orderHistory; // lysta me tis paragelies tou
    private int loyaltyPoints;

    // constructor
    public Customer(String email, String password, String phoneNumber, String address, int loyaltyPoints) {
        // kaloume ton constructor tis mamas klasis (User)
        super(email, password, phoneNumber, address);
        
        this.loyaltyPoints = loyaltyPoints;
        this.orderHistory = new ArrayList<>(); // arxikopoioume ti lista adeia
    }

    // upervasi ths abstract methodou ths mamas
    @Override
    public String getRoleLevel() {
        return "CUSTOMER";
    }

    // getters / setters
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public ArrayList<String> getOrderHistory() {
        return orderHistory;
    }

    // methodos gia na tou dinoume pontous apo tin pinata
    public void addPoints(int points) {
        this.loyaltyPoints += points;
    }
}