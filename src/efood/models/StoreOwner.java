package efood.models;

import java.util.ArrayList;

public class StoreOwner extends User {
    
    private String vatNumber;
    private String storeName;
    private ArrayList<String> managedStores;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }
    private Status approvalStatus;

    public StoreOwner(String fullName, String email, String password, String phoneNumber, String address, String vatNumber, String storeName) {
        super(fullName, email, password, phoneNumber, address); 
        this.vatNumber = vatNumber;
        this.storeName = storeName;
        this.managedStores = new ArrayList<>();
        this.approvalStatus = Status.PENDING; 
    }

    @Override
    public String getRoleLevel() {
        return "OWNER";
    }

    public String getVatNumber() { return vatNumber; }
    public String getStoreName() { return storeName; }
    
    public Status getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(Status approvalStatus) { this.approvalStatus = approvalStatus; }
}