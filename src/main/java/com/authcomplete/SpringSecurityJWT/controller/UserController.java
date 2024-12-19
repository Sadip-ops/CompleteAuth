package com.authcomplete.SpringSecurityJWT.controller;


import com.authcomplete.SpringSecurityJWT.dto.requestDTO.AuthRequestDto;
import com.authcomplete.SpringSecurityJWT.dto.requestDTO.RefreshTokenRequestDto;
import com.authcomplete.SpringSecurityJWT.dto.requestDTO.RoleRequest;
import com.authcomplete.SpringSecurityJWT.dto.requestDTO.UserRequest;
import com.authcomplete.SpringSecurityJWT.dto.responseDTO.JwtResponseDto;
import com.authcomplete.SpringSecurityJWT.dto.responseDTO.UserResponse;
import com.authcomplete.SpringSecurityJWT.entity.UserRole;
import com.authcomplete.SpringSecurityJWT.service.JwtService;
import com.authcomplete.SpringSecurityJWT.service.RefreshTokenService;
import com.authcomplete.SpringSecurityJWT.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage(), e);
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));

        // Get the authenticated user's details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Extract roles from the UserDetails object (assuming the roles are set in authorities)
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());  // Use Set instead of List

        // Generate JWT tokens
        String accessToken = jwtService.generateToken(authRequestDto.getUsername(), roles);
        String refreshToken = jwtService.generateRefreshToken(authRequestDto.getUsername());

        // Return the tokens
        return ResponseEntity.ok(new JwtResponseDto(accessToken, refreshToken));
    }


    @PostMapping("/refresh-token")
    @ResponseBody
    public ResponseEntity<JwtResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        log.info("refreshTokenRequestDto: {}", refreshTokenRequestDto);
        JwtResponseDto tokens = refreshTokenService.refreshAccessToken(refreshTokenRequestDto.getRefreshToken());
        if(tokens != null) {
            log.info("refreshToken: {}", tokens.getRefreshToken());
        }
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/getall")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.getAllUser();
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/retrieve/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }


    @DeleteMapping("/delete/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully with ID: " + id);
    }

    @PostMapping("/role/add")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserRole> addRole(@RequestBody RoleRequest roleRequest) {
        try {
            UserRole role = userService.addRole(roleRequest);
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @DeleteMapping("/roles/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        try {
            userService.deleteRoleById(id);
            return ResponseEntity.ok("Role deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
