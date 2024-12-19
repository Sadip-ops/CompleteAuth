package com.authcomplete.SpringSecurityJWT.service;

import com.authcomplete.SpringSecurityJWT.dto.responseDTO.JwtResponseDto;
import com.authcomplete.SpringSecurityJWT.entity.RefreshToken;
import com.authcomplete.SpringSecurityJWT.repository.RefreshTokenRepository;
import com.authcomplete.SpringSecurityJWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Value("${jwt.refreshexpiration}")
    private long expirationTime;

    public String createRefreshToken(String username) {
        return jwtService.generateRefreshToken(username);
    }

    public boolean validateRefreshToken(String token) {
        try {
            String username = jwtService.extractUsername(token);
            return username != null && !jwtService.isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public JwtResponseDto refreshAccessToken(String refreshToken) {
        // Validate the refresh token
        if (!validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid or expired refresh token.");
        }

        // Extract username from refresh token
        String username = jwtService.extractUsername(refreshToken);

        // Fetch roles from the database or another trusted source
        Set<String> roles = userRepository.findRolesByUsername(username);

        // Generate new access token and refresh token
        String newAccessToken = jwtService.generateToken(username, roles);
        String newRefreshToken = createRefreshToken(username);

        return JwtResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
