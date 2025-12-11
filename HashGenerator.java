package com.example.demo;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    /**
     * Generates a SHA-256 hash (fingerprint) from a byte array.
     * @param fileBytes The byte array of the document.
     * @return The hex string of the SHA-256 hash.
     */
    public static String generateSHA256Hash(byte[] fileBytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(fileBytes);
            
            // Convert byte array to a hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.", e);
        }
    }
    
    // (Optional: Keep the String version for backward compatibility)
    // public static String generateSHA256Hash(String fileContent) {
    //     return generateSHA256Hash(fileContent.getBytes());
    // }
}