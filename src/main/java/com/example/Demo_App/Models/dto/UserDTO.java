package com.example.Demo_App.Models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDTO {
    @NotBlank(message = "Email must not be empty.")
    @Email(message = "Invalid email format.")
    private String email;


    @Size(min = 6, message = "Password must be at least 6 characters long.")
    @NotBlank(message = "Password must not be empty.")
    private String password;
}
