package com.microshophub.userservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.microshophub.userservice.model.User;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationInMs;

    /**
     * Génère un token JWT à partir des informations de l'utilisateur.
     * Le token contiendra l'id de l'utilisateur comme "subject".
     * 
     * @param authentication L'authentification courante.
     * @return Le token JWT généré.
     */
    public String generateToken(User user) {
        Long userId = user.getId();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

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