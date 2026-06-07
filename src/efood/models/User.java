package efood.models;

// Abstract κλάση - Πατέρας για όλους τους χρήστες
public abstract class User {
    // Βασικά στοιχεία
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    // Constructor
    public User(String fullName, String email, String password, String phoneNumber, String address) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Abstract μέθοδος. Κάθε παιδί (Πελάτης, Οδηγός κτλ) τη γράφει αλλιώς
    public abstract String getRoleLevel();

    // Getters
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
}