package com.example.Demo_App.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    Long id;
    @Email
    String email;
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9_]{4,15}$",
            message = "Username must start with a letter, can include letters, numbers, underscores, and be 5–16 characters long"
    )
    String password;

}
