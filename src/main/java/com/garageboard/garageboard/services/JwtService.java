package com.garageboard.garageboard.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String key;

    public JwtService() {

    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(long id) {
        return Jwts.builder().subject(String.valueOf(id)).signWith(getSigningKey())
                .expiration(new Date(System.currentTimeMillis() + 86400000)).compact(); // 24 hours
    }

    public boolean validateToken(String token, String id) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject().equals(id);
    }

}
