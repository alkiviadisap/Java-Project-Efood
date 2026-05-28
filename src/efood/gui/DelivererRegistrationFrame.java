package efood.gui;

import efood.models.DeliveryDriver;
import efood.utils.DatabaseManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class DelivererRegistrationFrame extends JFrame {

    private JTextField nameField, emailField, passField, phoneField, addressField, licenseField, captchaInput;
    private JLabel captchaBox;
    private String currentCaptcha;
    private File selectedLicenseImage = null; 
    private File selectedCV = null; 

    public DelivererRegistrationFrame() {
        setTitle("Αίτηση Νέου Διανομέα");
        setSize(950, 850); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 242, 245));
        mainPanel.setBorder(new EmptyBorder(10, 220, 10, 220));

        JLabel titleLabel = new JLabel("Συμπληρώστε τα στοιχεία σας");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        nameField = new JTextField();
        emailField = new JTextField();
        passField = new JPasswordField();
        phoneField = new JTextField();
        addressField = new JTextField();
        licenseField = new JTextField();

        addFormInput(mainPanel, "Ονοματεπώνυμο:", nameField);
        addFormInput(mainPanel, "Email:", emailField);
        addFormInput(mainPanel, "Κωδικός Πρόσβασης:", passField);
        addFormInput(mainPanel, "Τηλέφωνο Επικοινωνίας:", phoneField);
        addFormInput(mainPanel, "Διεύθυνση Κατοικίας:", addressField);
        addFormInput(mainPanel, "Αριθμός Διπλώματος:", licenseField);

        JButton uploadImgBtn = new JButton("📷 Φωτογραφία Διπλώματος");
        uploadImgBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadImgBtn.setMaximumSize(new Dimension(500, 35));
        uploadImgBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedLicenseImage = fc.getSelectedFile();
                uploadImgBtn.setText("✅ " + selectedLicenseImage.getName());
                uploadImgBtn.setBackground(new Color(200, 255, 200));
            }
        });
        mainPanel.add(uploadImgBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton uploadCvBtn = new JButton("📄 Βιογραφικό Σημείωμα (PDF)");
        uploadCvBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadCvBtn.setMaximumSize(new Dimension(500, 35));
        uploadCvBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedCV = fc.getSelectedFile();
                uploadCvBtn.setText("✅ " + selectedCV.getName());
                uploadCvBtn.setBackground(new Color(200, 255, 200));
            }
        });
        mainPanel.add(uploadCvBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel captchaWrapper = new JPanel(new GridLayout(1, 2, 10, 0));
        captchaWrapper.setMaximumSize(new Dimension(500, 40));
        captchaWrapper.setOpaque(false);
        currentCaptcha = generateCaptcha(); 
        captchaBox = new JLabel(currentCaptcha, SwingConstants.CENTER);
        captchaBox.setOpaque(true);
        captchaBox.setBackground(new Color(200, 250, 220));
        captchaBox.setBorder(new LineBorder(new Color(150, 200, 170), 1));
        captchaBox.setFont(new Font("Monospaced", Font.BOLD, 18));
        captchaInput = new JTextField(); 
        captchaInput.setFont(new Font("Arial", Font.BOLD, 16));
        captchaWrapper.add(captchaBox);
        captchaWrapper.add(captchaInput);
        mainPanel.add(captchaWrapper);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton submitBtn = new JButton("ΥΠΟΒΟΛΗ ΑΙΤΗΣΗΣ");
        submitBtn.setBackground(new Color(255, 180, 180));
        submitBtn.setPreferredSize(new Dimension(950, 60));
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));

        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String pass = new String(((JPasswordField)passField).getPassword());
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            String license = licenseField.getText().trim();
            String userCaptcha = captchaInput.getText().trim();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || phone.isEmpty() || address.isEmpty() || license.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Συμπληρώστε όλα τα πεδία!"); return;
            }

            if (!name.matches("^[a-zA-Zα-ωΑ-ΩάέήίόύώςΆΈΉΊΌΎΏ\\s]+$")) {
                JOptionPane.showMessageDialog(null, "Το ονοματεπώνυμο πρέπει να περιέχει μόνο γράμματα!", "Λάθος Όνομα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!(email.endsWith("@gmail.com") || email.endsWith("@hotmail.com") || 
                  email.endsWith("@yahoo.gr") || email.endsWith("@outlook.com") || email.endsWith("@hmu.gr"))) {
                JOptionPane.showMessageDialog(null, "Βάλε ένα έγκυρο email (π.χ. @gmail.com)!", "Λάθος Email", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (phone.length() != 10 || !phone.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Λάθος τηλέφωνο.", "Λάθος", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedLicenseImage == null) {
                JOptionPane.showMessageDialog(null, "Ανεβάστε φωτογραφία διπλώματος!"); return;
            }
            if (selectedCV == null) {
                JOptionPane.showMessageDialog(null, "Ανεβάστε το Βιογραφικό σας (CV)!"); return;
            }
            
            if (!userCaptcha.equalsIgnoreCase(currentCaptcha.replace(" ", ""))) {
                JOptionPane.showMessageDialog(null, "Λάθος κωδικός CAPTCHA.");
                currentCaptcha = generateCaptcha(); 
                captchaBox.setText(currentCaptcha);
                captchaInput.setText(""); return;
            }

            String imagePath = selectedLicenseImage.getAbsolutePath();
            String cvPath = selectedCV.getAbsolutePath();
            
            DeliveryDriver newDriver = new DeliveryDriver(name, email, pass, phone, address, license, imagePath, cvPath);
            
            if (DatabaseManager.saveUser(newDriver)) {
                JOptionPane.showMessageDialog(null, "Αίτηση εστάλη! Εκκρεμεί έγκριση από Admin.");
                dispose();
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση.", "Λάθος", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = new JButton("Ακύρωση");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });
        mainPanel.add(backBtn);

        add(mainPanel, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);
    }

    private void addFormInput(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        field.setMaximumSize(new Dimension(500, 30));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private String generateCaptcha() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; 
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 5; i++) sb.append(chars.charAt(rnd.nextInt(chars.length()))).append(" ");
        return sb.toString().trim();
    }
}