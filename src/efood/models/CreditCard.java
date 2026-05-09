package efood.models;

public class CreditCard {
    
    // ta vasika stoixeia tis kartas
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private boolean isPreferred; // an einai i vasiki karta tou xristi

    // constructor
    public CreditCard(String cardNumber, String expirationDate, String cvv, boolean isPreferred) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.isPreferred = isPreferred;
    }

    // methodos gia plirwmi (pros to paron epistrefei panta true)
    public boolean processPayment() {
        // edw tha mporouse na ginetai elegxos me trapeza
        System.out.println("H plirwmi me tin karta " + cardNumber + " egkri8hke.");
        return true;
    }

    // --- getters & setters ---
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public boolean isPreferred() {
        return isPreferred;
    }

    public void setPreferred(boolean preferred) {
        isPreferred = preferred;
    }
}