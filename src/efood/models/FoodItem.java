package efood.models;

import java.util.ArrayList;

public class FoodItem extends Product {
    
    // extra pedia gia fagita
    private ArrayList<String> ingredients; // ta ulika (p.x. tomata, tyri)
    private boolean isVegan;
    private double discount;

    // constructor
    public FoodItem(String id, String title, double basePrice, String imagePath, boolean isVegan, double discount) {
        super(id, title, basePrice, imagePath); // stelnoume ta vasika stin mana
        this.isVegan = isVegan;
        this.discount = discount;
        this.ingredients = new ArrayList<>(); // arxikopoioume ti lista gia na min exoume null pointer
    }

    // upervasi tis methodou gia ton teliko upologismo tis timis
    @Override
    public double calculateFinalPrice() {
        // apli praxi: arxiki timi meion tin ekptwsi
        double finalPrice = basePrice - discount;
        
        // an gia kapoio logo vgei arnhtikh i timi (lathos), tin kanoume 0
        if (finalPrice < 0) {
            finalPrice = 0;
        }
        return finalPrice;
    }

    // --- getters & setters ---
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    // methodos gia na prosthetoume ulika sto piato
    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}