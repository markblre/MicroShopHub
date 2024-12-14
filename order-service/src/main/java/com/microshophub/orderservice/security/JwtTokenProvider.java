package com.microshophub.orderservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Extrait l'id de l'utilisateur du token JWT.
     * 
     * @param token Le token JWT.
     * @return L'id de l'utilisateur extrait du token.
     */
    public String getIdFromToken(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    /**
     * Valide le token en vérifiant sa signature et sa date d'expiration.
     * 
     * @param token Le token JWT.
     * @return true si le token est valide, false sinon.
     */
    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException ex) {
            // Le token est invalide ou expiré
            return false;
        }
    }

    /**
     * Extrait les "Claims" du token. S'il y a un problème (signature, expiration),
     * une JwtException sera lancée.
     * 
     * @param token Le token JWT.
     * @return Les "Claims" extraits du token.
     */
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}