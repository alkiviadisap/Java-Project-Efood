package efood.models;

// Κλάση για τον Ντελιβερά
public class DeliveryDriver extends User {
    
    private String vehicleLicense; // Πινακίδα
    private String imagePath; 
    private String cvPath; 
    
    // Κατάσταση έγκρισης από τον Admin
    public enum Status {
        PENDING, APPROVED, REJECTED
    }
    private Status approvalStatus;

    public DeliveryDriver(String fullName, String email, String password, String phoneNumber, String address, String vehicleLicense, String imagePath, String cvPath) {
        super(fullName, email, password, phoneNumber, address); 
        this.vehicleLicense = vehicleLicense;
        this.imagePath = imagePath;
        this.cvPath = cvPath; 
        this.approvalStatus = Status.PENDING; // Πρέπει να τον εγκρίνει ο Admin
    }

    @Override
    public String getRoleLevel() {
        return "DRIVER";
    }

    // Getters - Setters
    public String getVehicleLicense() { return vehicleLicense; }
    public void setVehicleLicense(String vehicleLicense) { this.vehicleLicense = vehicleLicense; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getCvPath() { return cvPath; }
    public void setCvPath(String cvPath) { this.cvPath = cvPath; }

    public Status getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(Status approvalStatus) { this.approvalStatus = approvalStatus; }
}