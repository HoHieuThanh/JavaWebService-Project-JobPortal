package com.ra.jobportal.auth.service;
import com.ra.jobportal.auth.dto.request.LoginRequest;
import com.ra.jobportal.auth.dto.request.RefreshTokenRequest;
import com.ra.jobportal.auth.dto.request.RegisterRequest;
import com.ra.jobportal.auth.dto.response.AuthResponse;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    void logout(String accessToken);
}
