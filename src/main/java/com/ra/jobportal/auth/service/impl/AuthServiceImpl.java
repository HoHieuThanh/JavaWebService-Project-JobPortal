package com.ra.jobportal.auth.service.impl;

import com.ra.jobportal.auth.dto.request.LoginRequest;
import com.ra.jobportal.auth.dto.request.RefreshTokenRequest;
import com.ra.jobportal.auth.dto.request.RegisterRequest;
import com.ra.jobportal.auth.dto.response.AuthResponse;
import com.ra.jobportal.auth.repository.RefreshTokenRepository;
import com.ra.jobportal.auth.repository.RoleRepository;
import com.ra.jobportal.auth.service.AuthService;
import com.ra.jobportal.entity.RefreshToken;
import com.ra.jobportal.entity.Role;
import com.ra.jobportal.entity.User;
import com.ra.jobportal.entity.enums.RoleName;
import com.ra.jobportal.user.repository.UserRepository;
import com.ra.jobportal.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository
                .findByName(
                        RoleName.valueOf(
                                request.getRole().toUpperCase()
                        )
                )
                .orElseThrow(
                        () -> new RuntimeException("Role not found")
                );

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .role(role)
                .build();

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtProvider.generateToken(request.getUsername());
        String refreshToken = jwtProvider.generateRefreshToken(request.getUsername());
        LocalDateTime refreshExpiry = LocalDateTime.now().plusNanos(
                jwtProvider.getRefreshExpirationMillis() * 1_000_000L
        );

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .token(refreshToken)
                        .user(user)
                        .expiryDate(refreshExpiry)
                        .build()
        );

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse refreshToken(
            RefreshTokenRequest request
    ) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(
                                request.getRefreshToken()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Refresh token invalid"
                                )
                        );

        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Refresh token expired"
            );
        }

        String username =
                jwtProvider.getUsernameFromToken(
                        request.getRefreshToken()
                );

        String newAccessToken =
                jwtProvider.generateToken(username);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .build();
    }
}