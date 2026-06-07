 package efood.models;

import java.util.ArrayList;
import java.util.Iterator;

// Το καλάθι του πελάτη
public class ShoppingCart {
    
    private ArrayList<Product> itemsList;
    private double totalAmount;
    private String appliedPromoCode; 

    public ShoppingCart() {
        this.itemsList = new ArrayList<>(); 
        this.totalAmount = 0.0;
        this.appliedPromoCode = "";
    }

    public void addItem(Product p) {
        itemsList.add(p);
        calculateTotal(); // Υπολογίζει ξανά το σύνολο
    }

    // Υπολογισμός με χρήση iterator (όπως ζητάει η εκφώνηση)
    public double calculateTotal() {
        totalAmount = 0.0; 
        
        Iterator<Product> iterator = itemsList.iterator();
        while (iterator.hasNext()) {
            Product currentItem = iterator.next();
            totalAmount += currentItem.calculateFinalPrice();
        }
        
        return totalAmount;
    }

    public ArrayList<Product> getItemsList() { return itemsList; }
    public double getTotalAmount() { return totalAmount; }

    public String getAppliedPromoCode() { return appliedPromoCode; }
    public void setAppliedPromoCode(String appliedPromoCode) { this.appliedPromoCode = appliedPromoCode; }
    
    // Αδειάζει το καλάθι
    public void clearCart() {
        itemsList.clear();
        totalAmount = 0.0;
        appliedPromoCode = "";
    }
}