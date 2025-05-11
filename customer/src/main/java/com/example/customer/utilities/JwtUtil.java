package com.example.customer.utilities;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final JwtUtil instance = new JwtUtil();
    private final Key key;

    private JwtUtil() {

        //this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // Use a fixed base64-encoded secret (at least 256 bits for HS256)
        String secret = "mysupersecretkeyformyjwtthatisverysecure1234"; // Replace with env variable in production
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public static JwtUtil getInstance() {
        return instance;
    }


//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
//                .signWith(key)
//                .compact();
//    }

    public String generateToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key)
                .compact();
    }

    public Map<String, String> validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.getSubject();
            String username = claims.get("username", String.class);

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", userId);
            userInfo.put("username", username);

            return userInfo;
        } catch (JwtException e) {
            return null;
        }
    }

//    public String validateTokenAndGetUsername(String token) {
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//        } catch (JwtException e) {
//            return null;
//        }
//    }
}
