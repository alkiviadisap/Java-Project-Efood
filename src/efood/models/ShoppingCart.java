package efood.models;

import java.util.ArrayList;
import java.util.Iterator;

public class ShoppingCart {
    
    // lista me ta proionta sto kalathi
    private ArrayList<Product> itemsList;
    private double totalAmount;
    private String appliedPromoCode; // an exei valei kwdiko ekptwsis

    // constructor
    public ShoppingCart() {
        this.itemsList = new ArrayList<>(); // arxika to kalathi einai adeio
        this.totalAmount = 0.0;
        this.appliedPromoCode = "";
    }

    // vazoume proion sto kalathi
    public void addItem(Product p) {
        itemsList.add(p);
        calculateTotal(); // ksanaupologizoume tin timi
    }

    // upologismos telikou posou me xrisi Iterator opws zitaei i ekfwnisi
    public double calculateTotal() {
        totalAmount = 0.0; // midenizoume prwta
        
        // ftiaxnoume enan iterator gia na diasxisoume ti lista me asfaleia
        Iterator<Product> iterator = itemsList.iterator();
        
        while (iterator.hasNext()) {
            Product currentItem = iterator.next();
            // kaloume tin polymorfiki methodo (den mas noiazei an einai food i drink)
            totalAmount += currentItem.calculateFinalPrice();
        }
        
        return totalAmount;
    }

    // --- getters & setters ---
    public ArrayList<Product> getItemsList() {
        return itemsList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getAppliedPromoCode() {
        return appliedPromoCode;
    }

    public void setAppliedPromoCode(String appliedPromoCode) {
        this.appliedPromoCode = appliedPromoCode;
    }
    
    // adeiasma kalathiou
    public void clearCart() {
        itemsList.clear();
        totalAmount = 0.0;
        appliedPromoCode = "";
    }
}