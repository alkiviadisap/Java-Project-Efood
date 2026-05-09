package efood.models;

public abstract class Product {
    // vasika xaraktiristika kathe proiontos
    protected String id;
    protected String title;
    protected double basePrice;
    protected String imagePath; // to path gia tin eikona tou proiontos

    // constructor
    public Product(String id, String title, double basePrice, String imagePath) {
        this.id = id;
        this.title = title;
        this.basePrice = basePrice;
        this.imagePath = imagePath;
    }

    // auti ti methodo thn ulopeioun ta paidia ths (polymorfismos)
    public abstract double calculateFinalPrice();

    // --- getters & setters ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}