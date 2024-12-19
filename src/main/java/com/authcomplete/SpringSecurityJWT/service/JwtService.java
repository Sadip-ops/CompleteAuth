package com.authcomplete.SpringSecurityJWT.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String token);

    Date extractExpiration(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Set<String> extractRoles(String token);

    boolean validateToken(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    String generateToken(String username, Set<String> roles);

    String generateRefreshToken(String username);
}
