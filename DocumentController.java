package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    // Instantiate your core Java logic class
    private final DocumentManager manager = new DocumentManager();
    
    // Admin function to approve a document (Added for full functionality)
    // Example: PUT http://localhost:8080/api/document/approve/123456?notes=Verified
    @PutMapping("/approve/{documentId}")
    public ResponseEntity<String> approveDocument(@PathVariable String documentId, @RequestParam String notes) {
        if (manager.approveDocument(documentId, notes)) {
            return ResponseEntity.ok("Document " + documentId + " approved.");
        }
        return ResponseEntity.badRequest().body("Document not found or already processed.");
    }
    
    // Maps to the uploadForm's action="/api/document/upload"
    // This handles the file upload (MultipartFile) and other form fields (@RequestParam)
    @PostMapping("/upload")
    public ResponseEntity<Document> handleUpload(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        
        try {
            // Convert the uploaded file into a byte array
            byte[] fileBytes = file.getBytes();
            String filename = file.getOriginalFilename();
            
            // Call your core business logic
            Document newDoc = manager.uploadDocument(filename, userId, fileBytes);
            
            if (newDoc == null) {
                 // DocumentManager returns null if it's a duplicate hash
                return ResponseEntity.status(409).body(null); // HTTP 409 Conflict
            }

            return ResponseEntity.ok(newDoc); // Returns the uploaded document JSON
            
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // HTTP 500 Internal Server Error
        }
    }

    // Maps to the statusForm's action="/api/document/status" (using GET)
    // Example: GET http://localhost:8080/api/document/status?documentId=123456
    @GetMapping("/status")
    public ResponseEntity<Document> getStatus(@RequestParam String documentId) {
        Document doc = manager.getDocumentStatus(documentId);
        if (doc != null) {
            return ResponseEntity.ok(doc);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Optional: Get all documents for a user
    @GetMapping("/user/{userId}")
    public List<Document> getUserDocuments(@PathVariable String userId) {
        return manager.getUserDocuments(userId);
    }
}