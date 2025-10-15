package com.example.Demo_App.Controller;

import com.example.Demo_App.Models.User;
import com.example.Demo_App.Repository.UserRepository;
import com.example.Demo_App.Security.SecurityConfig;
import com.example.Demo_App.Service.UserService;
import com.example.Demo_App.utilis.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String,String>body)
    {
        String email = body.get("email");
        String password = body.get("password");
        if(userRepository.findByEmail(email).isPresent())
        {
            return  new ResponseEntity<>("Email already Exists",HttpStatus.CONFLICT);
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
            return new ResponseEntity<>("User is not Registered",HttpStatus.UNAUTHORIZED);
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
