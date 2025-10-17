package com.example.Demo_App.utilis;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "ToDo Demo App token is ready for login the application";
    private final long EXPIRATION = 1000*60*30;
    private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email)
    {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secretKey)
                .compact();

    }
    public String extractToken(String token)
    {
       return  Jwts.parser().setSigningKey(secretKey)
               .build()
               .parseSignedClaims(token)
               .getBody()
               .getSubject();
    }
    public boolean ValidateToken(String token)
    {
        try{
            extractToken(token);

          return true;
        }
        catch (JwtException e)
        {
            return false;
        }

    }
}
