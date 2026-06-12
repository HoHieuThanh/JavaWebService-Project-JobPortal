package com.ra.jobportal.auth.service;
import com.ra.jobportal.auth.dto.request.*;
import com.ra.jobportal.auth.dto.response.AuthResponse;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    void logout(String accessToken);
    void changePassword(ChangePasswordRequest request, String username);
    String forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
}
