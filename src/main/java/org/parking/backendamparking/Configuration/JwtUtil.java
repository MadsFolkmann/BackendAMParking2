package org.parking.backendamparking.Configuration;

import org.parking.backendamparking.Entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "AMKingsParkingSecretKey"; // Skal være stored sikkert i en miljøvariabel eller konfigurationsfil til produktion

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 timer
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
