package com.revature.yield.services;

import com.revature.yield.dtos.response.Principal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenService {

    private final Key SECRET_KEY;

    // inject the secret key from yml file and then hash the secret key
    public JwtTokenService(@Value("${jwt.secret}") String secret) {
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a JWT token based on the provided user principal.
     *
     * @param userPrincipal The user principal.
     * @return The generated JWT token.
     */
    public String generateToken(Principal userPrincipal) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userPrincipal.getId());
        claims.put("role", userPrincipal.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Set expiration time to 10 hours
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * Validates the provided JWT token against the user principal.
     *
     * @param token         The JWT token to validate.
     * @param userPrincipal The user principal to compare against.
     * @return true if the token is valid for the user principal, false otherwise.
     */
    public boolean validateToken(String token, Principal userPrincipal) {
        String tokenUsername = extractUsername(token);
        return tokenUsername.equals(userPrincipal.getUsername());
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The extracted username.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a claim from the JWT token using the provided claims resolver
     * function.
     *
     * @param token          The JWT token.
     * @param claimsResolver The claims resolver function.
     * @param <T>            The type of the claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token.
     * @return The extracted claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token).getBody();
    }

    /**
     * Extracts the user ID from the JWT token.
     *
     * @param token The JWT token.
     * @return The extracted user ID.
     */
    public String extractUserId(String token) {
        return (String) extractAllClaims(token).get("id");
    }

    /**
     * Extracts the user role from the JWT token.
     *
     * @param token The JWT token.
     * @return The extracted user role.
     */
    public String extractUserRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }
}
