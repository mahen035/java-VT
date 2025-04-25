package com.training.springboot.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    // Generate a secure key. For production, store this key securely and consistently.
   // private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final SecretKey key = Jwts.SIG.HS512.key().build();
    private Set<String> revokedTokens = new HashSet<>();
    private static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(30); // 30 minutes
    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME)))
                .signWith(key)
                .compact();
    }
    public String extractUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims =  Jwts.parser().verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }


    //    public String extractJti(String token) {
//
//        return parseToken(token).get("jti", String.class);
//    }
    public boolean validateToken(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!Objects.isNull(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public void revokeToken(String token) {
        revokedTokens.add(token);
        System.out.println("Token revoked successfully");
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }
//    private boolean isTokenExpired(String token) {
//        Date expiration = Jwts.parserBuilder().setSigningKey(key).build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//        return expiration.before(new Date());
//    }
//    private Claims parseToken(String token) {
//        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//    }
}
