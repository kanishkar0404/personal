package com.example.demo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path; // <-- FIX: Added this critical import

public class Main {
    
    // Helper method to read a file and handle errors
    private static byte[] readFileToBytes(String filePath) {
        Path path = Path.of(filePath);
        try {
            // This is the line that requires the java.nio.file.Files import
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("❌ ERROR: Could not read file at path: " + filePath);
            System.err.println("Please ensure the file exists in the correct location.");
            e.printStackTrace();
            return null; // Return null on failure
        }
    }

    public static void main(String[] args) {
        DocumentManager manager = new DocumentManager();
        String userA = "user_A001";
        
        // =========================================================================
        // IMPORTANT: PLACE A FILE NAMED 'diploma.pdf' IN THE SAME DIRECTORY 
        // AS YOUR JAVA FILES BEFORE RUNNING. (Also contract.docx)
        // =========================================================================
        String filePath1 = "diploma.pdf"; 
        String filePath2 = "contract.docx";
        
        // 1. Read actual files from disk
        byte[] content1 = readFileToBytes(filePath1);
        byte[] content2 = readFileToBytes(filePath2);
        
        if (content1 == null || content2 == null) {
            System.out.println("\n--- EXECUTION HALTED DUE TO FILE READ ERROR. ---");
            return;
        }

        System.out.println("--- 1. UPLOAD STAGE (Reading Actual Files) ---");
        
        // Document 1: Original upload
        Document doc1 = manager.uploadDocument(filePath1, userA, content1);

        // Document 2: Another unique upload
        Document doc2 = manager.uploadDocument(filePath2, userA, content2);

        // Document 3: Fake/Duplicate upload attempt (using same content as doc1)
        System.out.println("\n--- DUPLICATE UPLOAD ATTEMPT ---");
        manager.uploadDocument("Fake_File.jpg", "user_B002", content1); // Same content as doc1

        // ... (The rest of the tracking/verification logic remains the same)
        if (doc1 != null) {
            System.out.println("\n--- 2. USER TRACKING STAGE ---");
            // ... (Rest of the original Main logic here)
            
            System.out.println("**User Status Check:** " + manager.getDocumentStatus(doc1.getDocumentId()).getStatus());
            
            // ... (Admin approval/rejection)

            manager.approveDocument(doc1.getDocumentId(), "All required stamps verified.");
            manager.rejectDocument(doc2.getDocumentId(), "Signature missing on page 3.");
            
            System.out.println("\n--- 4. POST-VERIFICATION STAGE ---");
            System.out.println("**User Status Check (Approved):** " + manager.getDocumentStatus(doc1.getDocumentId()).getStatus());
            System.out.println("**User Download Attempt (Approved):** " + manager.downloadFile(doc1.getDocumentId()));
        }
        
        System.out.println("\n--- 5. FINAL STATUS ---");
        manager.getUserDocuments(userA).forEach(System.out::println);
    }
}