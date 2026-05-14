package efood.models;

public abstract class User {
    // vasika stoixeia pou exoun oloi oi xristes
    protected String fullName; // to neo pedio pou prosthesame
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected String address;

    // constructor
    public User(String fullName, String email, String password, String phoneNumber, String address) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // auth tin methodo prepei na tin ftiaxoun oles oi upoklaseis upoxrewtika
    public abstract String getRoleLevel();

    // -- getters & setters --
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}