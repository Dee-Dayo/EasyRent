package com.semicolon.EaziRent.controllers;

import com.semicolon.EaziRent.dtos.requests.LoginRequest;
import com.semicolon.EaziRent.security.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.semicolon.EaziRent.security.utils.SecurityUtils.JWT_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(JWT_PREFIX)) {
            String token = authHeader.replace(JWT_PREFIX, "").strip();
            authService.blacklist(token);
            SecurityContextHolder.clearContext();
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
