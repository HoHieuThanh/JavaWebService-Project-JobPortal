package com.ra.jobportal.auth.controller;

import com.ra.jobportal.auth.dto.request.*;
import com.ra.jobportal.auth.dto.response.AuthResponse;
import com.ra.jobportal.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return "Register success";
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        String token = header.substring(7);
        authService.logout(token);
        return "Logout success";
    }

    @PutMapping("/change-password")
    public String changePassword(

            @Valid
            @RequestBody
            ChangePasswordRequest request,

            Authentication authentication
    ) {

        authService.changePassword(
                request,
                authentication.getName()
        );

        return "Đổi mật khẩu thành công";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(
            @Valid
            @RequestBody
            ForgotPasswordRequest request
    ) {

        return authService
                .forgotPassword(
                        request
                );
    }
    @PostMapping("/reset-password")
    public String resetPassword(
            @Valid
            @RequestBody
            ResetPasswordRequest request
    ) {

        authService
                .resetPassword(
                        request
                );

        return "Đổi mật khẩu thành công";
    }
}