package com.example.Demo_App.Controller;

import com.example.Demo_App.Models.User;
import com.example.Demo_App.Models.dto.UserDTO;
import com.example.Demo_App.Repository.UserRepository;
import com.example.Demo_App.Security.SecurityConfig;
import com.example.Demo_App.Service.UserService;
import com.example.Demo_App.Validation.ValidationUtil;
import com.example.Demo_App.utilis.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String,String> body)
    {
        String email = body.get("email");
        String password = body.get("password");
        ResponseEntity<Map<String, String>> validationResponse = ValidationUtil.validateCredentials(email, password);
        if (validationResponse != null) {
            return validationResponse;
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("error_message", "Email already exists"));
        }

        userService.createUser(User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build());

        return ResponseEntity.ok(Map.of("message", "Email is registered successfully!"));

    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody  Map<String,String> body)
    {
        String email = body.get("email");
        String password = body.get("password");
        ResponseEntity<Map<String, String>> validationResponse = ValidationUtil.validateCredentials(email, password);
        if (validationResponse != null) {
            return validationResponse;
        }
        if(userRepository.findByEmail(email).isEmpty())
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error_message", "User is not registered"));
        }
        User user = userRepository.findByEmail(email).get();
        if (!passwordEncoder.matches(password, user.getPassword()))
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error_message", "Invalid user credentials"));
        }
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token",token));


    }
}
