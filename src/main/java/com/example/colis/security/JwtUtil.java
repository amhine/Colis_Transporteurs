package com.example.colis.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.colis.model.Enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.issuer}")
    private String issuer;

    public String generateToken(String login, Role role) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(login)
                .withClaim("role", role.name())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String extractLogin(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    public Role extractRole(String token) {
        DecodedJWT jwt = JWT.decode(token);
        String roleName = jwt.getClaim("role").asString();
        return Role.valueOf(roleName);
    }
}
