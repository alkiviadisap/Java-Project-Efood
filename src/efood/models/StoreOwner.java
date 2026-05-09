package efood.models;

import java.util.ArrayList;

public class StoreOwner extends User {
    
    // extra pedia gia to afm kai ta magazia
    private String vatNumber;
    private ArrayList<String> managedStores;

    // constructor
    public StoreOwner(String email, String password, String phoneNumber, String address, String vatNumber) {
        super(email, password, phoneNumber, address); // kaloume to User
        this.vatNumber = vatNumber;
        this.managedStores = new ArrayList<>();
    }

    @Override
    public String getRoleLevel() {
        return "OWNER";
    }

    // getters / setters
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public ArrayList<String> getManagedStores() {
        return managedStores;
    }
}