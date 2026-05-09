package efood.models;

public class DeliveryDriver extends User {
    
    // extra pedia gia ton odigo
    private String vehicleLicense;
    
    // ftiaxnoume ena aplo Enum gia tin katastasi tou odigou opws leei i ekfwnisi
    public enum Status {
        PENDING, APPROVED, REJECTED
    }
    private Status approvalStatus;

    // constructor
    public DeliveryDriver(String email, String password, String phoneNumber, String address, String vehicleLicense) {
        super(email, password, phoneNumber, address); // paei stin mana (User)
        this.vehicleLicense = vehicleLicense;
        this.approvalStatus = Status.PENDING; // otan kanei eggrafi einai panta se anamoni
    }

    @Override
    public String getRoleLevel() {
        return "DRIVER";
    }

    // --- getters & setters ---
    public String getVehicleLicense() {
        return vehicleLicense;
    }

    public void setVehicleLicense(String vehicleLicense) {
        this.vehicleLicense = vehicleLicense;
    }

    public Status getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Status approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}