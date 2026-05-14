package efood.models;

public class CreditCard {
    
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private boolean isPreferred; 

    public CreditCard(String cardNumber, String expirationDate, String cvv, boolean isPreferred) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.isPreferred = isPreferred;
    }

    // prosomoiwsi plirwmis
    public boolean processPayment() {
        System.out.println("Plirwmi me karta " + cardNumber + " egkrithike.");
        return true;
    }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    public boolean isPreferred() { return isPreferred; }
    public void setPreferred(boolean preferred) { this.isPreferred = preferred; }
}