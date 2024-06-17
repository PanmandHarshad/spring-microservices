package com.example.basic.and.jwt.token.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for handling JWT operations such as generating tokens, extracting claims,
 * and validating tokens.
 */
@Component
public class JwtService {

    public static final String SECRET = "75580004dbf17cd72c2fede40e0100772f3cc66716b806b6a344a1e37b407cf9";

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token.
     * @return the expiration date of the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided claims resolver function.
     *
     * @param token          the JWT token.
     * @param claimsResolver the function to extract the desired claim.
     * @param <T>            the type of the claim to be extracted.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token.
     * @return the claims extracted from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token the JWT token.
     * @return true if the token has expired, false otherwise.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates the JWT token by checking the username and expiration date.
     *
     * @param token       the JWT token.
     * @param userDetails the user details to validate against.
     * @return true if the token is valid, false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Generates a new JWT token for the given username.
     *
     * @param userName the username for which the token is to be generated.
     * @return the generated JWT token.
     */
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    /**
     * Creates a JWT token with the specified claims and subject (username).
     *
     * @param claims   the claims to include in the token.
     * @param username the subject (username) for the token.
     * @return the created JWT token.
     */
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey()).compact();
    }

    /**
     * Retrieves the signing key used to sign the JWT token.
     *
     * @return the signing key.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
