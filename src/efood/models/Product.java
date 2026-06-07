package efood.models;

// Abstract κλάση για τα προϊόντα
public abstract class Product {
    protected String id;
    protected String title;
    protected double basePrice;
    protected String imagePath; 

    public Product(String id, String title, double basePrice, String imagePath) {
        this.id = id;
        this.title = title;
        this.basePrice = basePrice;
        this.imagePath = imagePath;
    }

    // Πολυμορφική μέθοδος. Ο υπολογισμός γίνεται αλλιώς σε κάθε παιδί.
    public abstract double calculateFinalPrice();

    // Getters και Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}