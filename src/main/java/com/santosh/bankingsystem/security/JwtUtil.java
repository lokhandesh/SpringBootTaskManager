package com.santosh.bankingsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "mybankingappjwtsecretkey1234567890"; // use 256-bit (32 chars+)
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            return parseToken(token).getBody().getSubject();
        } catch (Exception ex) {
            System.out.println("JWT extractUsername error: " + ex.getMessage());
            return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception ex) {
            System.out.println("JWT isTokenValid error: " + ex.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return parseToken(token).getBody().getExpiration().before(new Date());
        } catch (Exception ex) {
            System.out.println("JWT isTokenExpired error: " + ex.getMessage());
            return true;
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}