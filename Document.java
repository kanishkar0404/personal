package com.example.demo;
import java.time.LocalDateTime;

public class Document {
    private String documentId;
    private String filename;
    private String userId;
    private String fileHash; // The unique fingerprint (SHA-256)
    private DocumentStatus status;
    private LocalDateTime uploadDate;
    private String adminNotes;

    // Enum for document status
    public enum DocumentStatus {
        PENDING_REVIEW, APPROVED, REJECTED
    }

    // Constructor
    public Document(String documentId, String filename, String userId, String fileHash) {
        this.documentId = documentId;
        this.filename = filename;
        this.userId = userId;
        this.fileHash = fileHash;
        this.status = DocumentStatus.PENDING_REVIEW; // Default status
        this.uploadDate = LocalDateTime.now();
        this.adminNotes = "";
    }

    // --- Getters and Setters ---
    public String getDocumentId() { return documentId; }
    public String getFilename() { return filename; }
    public String getUserId() { return userId; }
    public String getFileHash() { return fileHash; }
    public DocumentStatus getStatus() { return status; }
    public LocalDateTime getUploadDate() { return uploadDate; }
    public String getAdminNotes() { return adminNotes; }

    public void setStatus(DocumentStatus status) { this.status = status; }
    public void setAdminNotes(String adminNotes) { this.adminNotes = adminNotes; }

    @Override
    public String toString() {
        return "Document{" +
                "id='" + documentId + '\'' +
                ", name='" + filename + '\'' +
                ", user='" + userId + '\'' +
                ", hash='" + fileHash.substring(0, 10) + "...'" +
                ", status=" + status +
                '}';
    }
}