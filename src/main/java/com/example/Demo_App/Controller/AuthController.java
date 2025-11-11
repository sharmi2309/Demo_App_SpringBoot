package com.example.Demo_App.Controller;

import com.example.Demo_App.Models.User;
import com.example.Demo_App.Repository.UserRepository;
import com.example.Demo_App.Security.SecurityConfig;
import com.example.Demo_App.Service.UserService;
import com.example.Demo_App.utilis.JwtUtil;
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
    public ResponseEntity<String> registerUser(@RequestBody Map<String,String> body)
    {
        String email = body.get("email");
        String password = passwordEncoder.encode(body.get("password"));
        if(userRepository.findByEmail(email).isPresent())
        {
            return new ResponseEntity<>("Email already Exists",HttpStatus.CONFLICT);
        }
        userService.createUser(User.builder().email(email).password(password).build());
        return new ResponseEntity<>("Email is Registered Successfully !!",HttpStatus.OK);

    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody  Map<String,String> body)
    {
        String email = body.get("email");
        String password = body.get("password");
        if(userRepository.findByEmail(email).isEmpty())
        {
            return new ResponseEntity<>("User is not Registered",HttpStatus.NOT_FOUND);
        }
        User user = userRepository.findByEmail(email).get();
        if (!passwordEncoder.matches(password, user.getPassword()))
        {
            return new ResponseEntity<>("Invalid User",HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token",token));


    }
}
