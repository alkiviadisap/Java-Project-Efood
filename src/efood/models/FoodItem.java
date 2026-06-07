package efood.models;

import java.util.ArrayList;

// Κληρονομεί από το Product
public class FoodItem extends Product {
    
    private ArrayList<String> ingredients; 
    private boolean isVegan;
    private double discount;

    public FoodItem(String id, String title, double basePrice, String imagePath, boolean isVegan, double discount) {
        super(id, title, basePrice, imagePath); 
        this.isVegan = isVegan;
        this.discount = discount;
        this.ingredients = new ArrayList<>(); 
    }

    // Overriding τη μέθοδο του Product
    @Override
    public double calculateFinalPrice() {
        double finalPrice = basePrice - discount;
        // Ασφάλεια για να μην βγει αρνητική τιμή
        if (finalPrice < 0) {
            finalPrice = 0;
        }
        return finalPrice;
    }

    public ArrayList<String> getIngredients() { return ingredients; }
    public void addIngredient(String ingredient) { this.ingredients.add(ingredient); }

    public boolean isVegan() { return isVegan; }
    public void setVegan(boolean vegan) { this.isVegan = vegan; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
}