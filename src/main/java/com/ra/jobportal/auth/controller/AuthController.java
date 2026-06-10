package com.ra.jobportal.auth.controller;

import com.ra.jobportal.auth.dto.request.LoginRequest;
import com.ra.jobportal.auth.dto.request.RefreshTokenRequest;
import com.ra.jobportal.auth.dto.request.RegisterRequest;
import com.ra.jobportal.auth.dto.response.AuthResponse;
import com.ra.jobportal.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request
    ) {

        authService.register(request);

        return "Register success";
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request
    ) {

        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {

        return authService.refreshToken(request);
    }
}