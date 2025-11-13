package com.example.Demo_App.Validation;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ValidationUtil {
    public static ResponseEntity<Map<String, String>> validateCredentials(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error_message", "Email must not be empty."));
        }

        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error_message", "Password must not be empty."));
        }

        if (password.length() < 6) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error_message", "Password must be at least 6 characters long."));
        }

        return null;
    }
}
