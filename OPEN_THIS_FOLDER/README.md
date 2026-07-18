# Java-Project-Efood
======================================================
      ΣΥΣΤΗΜΑ ΠΑΡΑΓΓΕΛΙΩΝ ΕΣΤΙΑΣΗΣ (eFood Project)
      Αναφορά Παραδοτέου: Φάση 3 (Τελική)
======================================================

1. ΤΕΧΝΙΚΕΣ ΠΛΗΡΟΦΟΡΙΕΣ
------------------------------------------------------
Το παρόν project αποτελεί το τελικό παραδοτέο της 3ης Φάσης. 
- Περιβάλλον Ανάπτυξης: Apache NetBeans IDE.
- Γλώσσα: Java (Swing/AWT με ενσωμάτωση Nimbus Theme για μοντέρνο UI).
- Αποθήκευση Δεδομένων: Τοπικά αρχεία CSV (Data Persistence) με προηγμένο Data Sanitization.

2. ΤΡΟΠΟΙ ΕΚΤΕΛΕΣΗΣ
------------------------------------------------------
Υπάρχουν δύο τρόποι για τον έλεγχο της εφαρμογής:

Α. Μέσω NetBeans (για έλεγχο κώδικα):
   - Ανοίξτε το project στο NetBeans.
   - Εκτελέστε το αρχείο: src/efood/main/EfoodApp.java

Β. Μέσω Εκτελέσιμου (για άμεση δοκιμή):
   - Μεταβείτε στον φάκελο: /OPEN_THIS_FOLDER
   - Εκτελέστε το αρχείο: run_efood.bat (αντίστοιχα για MacBook το run_efood_FOR_MACBOOK.sh) ή κατευθείαν το eFoodApp.jar
   * Σημείωση: Ο φάκελος "data/" πρέπει να βρίσκεται στον ίδιο κατάλογο με το αρχείο .jar.

3. ΠΕΡΙΓΡΑΦΗ ΥΛΟΠΟΙΗΣΗΣ (SCOPE ΦΑΣΗΣ 3)
------------------------------------------------------
Στο παρόν τελικό παραδοτέο (Φάση 3) έχει ολοκληρωθεί πλήρως η εφαρμογή με όλες τις προηγμένες λειτουργίες που απαιτούνται:
- Multithreading (Background Threads): Υλοποίηση στο σύστημα πληρωμών του Ταμείου για ρεαλιστική προσομοίωση επεξεργασίας, χωρίς να "παγώνει" το UI.
- Αλγόριθμοι Εκπτώσεων & Loyalty: Δυναμικός υπολογισμός στο καλάθι (με χρήση HashSet για τα κουπόνια και απόλυτη ακρίβεια μαθηματικών μέσω Locale.US).
- Animation & Παιχνιδοποίηση (Gamification): Υλοποίηση κινούμενης "Lucky Pinata" με χρήση αντικειμένου Timer της Java.
- Πλήρης Διασύνδεση Ρόλων: Η παραγγελία του Πελάτη μεταφέρεται σε πραγματικό χρόνο στον πίνακα του Διανομέα και ενημερώνεται η κατάστασή της (PENDING / COMPLETED).
- Ασφαλής Διαχείριση CSV (Defensive Programming): Προστασία των αρχείων με αυτόματο καθαρισμό κομμάτων (replace) και προστασία από ArrayIndexOutOfBoundsException.

4. ΔΕΔΟΜΕΝΑ ΔΟΚΙΜΗΣ (Test Accounts)
------------------------------------------------------
Τα αρχεία στον φάκελο "data/" περιέχουν προ-συμπληρωμένα δεδομένα για την ευκολότερη αξιολόγηση:

- ADMIN: email: admin@efood.gr / pass: admin123
- ΠΕΛΑΤΗΣ: email: user@efood.com / pass: pass123
- ΚΑΤΑΣΤΗΜΑ: email: owner@burgerjoint.com / pass: admin123
- ΔΙΑΝΟΜΕΑΣ: email: driver@gmail.com / pass: pass123

5. ΒΟΗΘΗΤΙΚΑ ΑΡΧΕΙΑ
------------------------------------------------------
Στον φάκελο "forUSE" υπάρχουν έτοιμα δοκιμαστικά αρχεία (PDF βιογραφικό & Εικόνα διπλώματος) για τον έλεγχο της φόρμας εγγραφής διανομέα και της προβολής τους από το Admin Dashboard.

6. ΤΕΚΜΗΡΙΩΣΗ
------------------------------------------------------
- Το αρχείο "Αναφορά_Φάσης_3_eFood_Project.pdf" περιλαμβάνει την πλήρη τεκμηρίωση της τελικής αρχιτεκτονικής (Class Diagrams), επεξήγηση του Multithreading, τα νέα screenshots της εφαρμογής και την αντιστοίχιση με τα κριτήρια βαθμολόγησης.
======================================================

======================================================
⚠️ LEGAL DISCLAIMER & ACADEMIC CONTEXT
======================================================
This software was developed solely as an educational project for an academic course at the Department of Electrical and Computer Engineering (Hellenic Mediterranean University). 

Disclaimer Regarding Trademarks: 
The name "efood" (or any related branding/assets used within this application) is utilized strictly for academic, simulation, and demonstrative purposes. This project is entirely non-commercial and is NOT affiliated with, sponsored by, authorized by, or in any way officially connected to the actual "efood" brand, Delivery Hero, or any of its subsidiaries. All registered trademarks and copyrights belong to their respective owners. No financial gain is generated from this project.
======================================================


ENGLISH

# Java-Project-Efood
======================================================
      FOOD DELIVERY ORDERING SYSTEM (eFood Project)
      Deliverable Report: Phase 3 (Final)
======================================================

1. TECHNICAL INFORMATION
------------------------------------------------------
This project is the final deliverable for Phase 3. 
- Development Environment: Apache NetBeans IDE.
- Language: Java (Swing/AWT with Nimbus Theme integration for a modern UI).
- Data Storage: Local CSV files (Data Persistence) with advanced Data Sanitization.

2. EXECUTION INSTRUCTIONS
------------------------------------------------------
There are two ways to test the application:

A. Via NetBeans (for code review):
   - Open the project in NetBeans.
   - Run the file: src/efood/main/EfoodApp.java

B. Via Executable (for quick testing):
   - Navigate to the folder: /OPEN_THIS_FOLDER
   - Run the file: run_efood.bat (or run_efood_FOR_MACBOOK.sh for macOS) or directly execute eFoodApp.jar
   * Note: The "data/" folder must be located in the same directory as the .jar file.

3. IMPLEMENTATION DETAILS (PHASE 3 SCOPE)
------------------------------------------------------
In this final deliverable (Phase 3), the application has been fully completed with all the required advanced features:
- Multithreading (Background Threads): Implemented in the Checkout payment system for a realistic processing simulation, without freezing the UI.
- Discount Algorithms & Loyalty: Dynamic cart calculations (using HashSet for coupons and absolute mathematical precision via Locale.US).
- Animation & Gamification: Implementation of an animated "Lucky Pinata" using the Java Timer object.
- Full Role Interconnection: Customer orders are transferred in real-time to the Delivery Driver's dashboard, instantly updating their status (PENDING / COMPLETED).
- Secure CSV Management (Defensive Programming): File protection with automatic comma sanitization (replace) and prevention of ArrayIndexOutOfBoundsException.

4. TEST DATA (Test Accounts)
------------------------------------------------------
The files in the "data/" folder contain pre-filled data for easier evaluation:

- ADMIN: email: admin@efood.gr / pass: admin123
- CUSTOMER: email: user@efood.com / pass: pass123
- STORE OWNER: email: owner@burgerjoint.com / pass: admin123
- DELIVERY DRIVER: email: driver@gmail.com / pass: pass123

5. AUXILIARY FILES
------------------------------------------------------
In the "forUSE" folder, there are ready-to-use test files (a PDF resume & a Driver's License image) for testing the delivery driver registration form and viewing the uploaded files from the Admin Dashboard.

6. DOCUMENTATION
------------------------------------------------------
- The file "Αναφορά_Φάσης_3_eFood_Project.pdf" includes the full documentation of the final architecture (Class Diagrams), an explanation of the Multithreading implementation, new application screenshots, and the alignment with the grading criteria.
======================================================

======================================================
⚠️ LEGAL DISCLAIMER & ACADEMIC CONTEXT
======================================================
This software was developed solely as an educational project for an academic course at the Department of Electrical and Computer Engineering (Hellenic Mediterranean University). 

Disclaimer Regarding Trademarks: 
The name "efood" (or any related branding/assets used within this application) is utilized strictly for academic, simulation, and demonstrative purposes. This project is entirely non-commercial and is NOT affiliated with, sponsored by, authorized by, or in any way officially connected to the actual "efood" brand, Delivery Hero, or any of its subsidiaries. All registered trademarks and copyrights belong to their respective owners. No financial gain is generated from this project.
======================================================
