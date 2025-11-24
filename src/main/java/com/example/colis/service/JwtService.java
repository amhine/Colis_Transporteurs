package com.example.colis.service;

import com.example.colis.model.Enums.Role;
import com.example.colis.model.User;
import io.jsonwebtoken.Claims;


public interface JwtService {
    String generateToken(User user);
    boolean validateToken(String token);
    String extractUsername(String token);
    Role extractRole(String token);
    Claims extractAllClaims(String token);
    boolean isTokenExpired(String token);
}
