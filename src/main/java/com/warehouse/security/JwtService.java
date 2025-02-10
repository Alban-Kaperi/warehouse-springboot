package com.warehouse.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username) {
        return "Bearer " + username;
    }

    public String createToken(String username) {
        return "Bearer " + username;
    }

    public boolean isTokenValid(String token) {
        return true;
    }

    public boolean isTokenExpired(String token) {
        return false;
    }




}
