package com.ra.jobportal.auth.service;

import com.ra.jobportal.auth.dto.request.RegisterRequest;
import com.ra.jobportal.auth.repository.*;
import com.ra.jobportal.auth.service.impl.AuthServiceImpl;
import com.ra.jobportal.entity.Role;
import com.ra.jobportal.entity.User;
import com.ra.jobportal.entity.enums.RoleName;
import com.ra.jobportal.exception.BadRequestException;
import com.ra.jobportal.security.jwt.JwtProvider;
import com.ra.jobportal.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Test
    void register_success() {

        RegisterRequest request =
                new RegisterRequest();

        request.setUsername("candidate");
        request.setEmail("candidate@gmail.com");
        request.setPassword("123456");
        request.setRole("CANDIDATE");

        when(userRepository.existsByUsername(anyString()))
                .thenReturn(false);

        when(userRepository.existsByEmail(anyString()))
                .thenReturn(false);

        when(roleRepository.findByName(RoleName.CANDIDATE))
                .thenReturn(
                        Optional.of(
                                Role.builder()
                                        .name(RoleName.CANDIDATE)
                                        .build()
                        )
                );

        when(passwordEncoder.encode(anyString()))
                .thenReturn("encoded");

        authService.register(request);

        verify(userRepository)
                .save(any(User.class));
    }

    @Test
    void register_duplicate_username() {

        RegisterRequest request =
                new RegisterRequest();

        request.setUsername("admin");

        when(userRepository.existsByUsername("admin"))
                .thenReturn(true);

        assertThrows(
                BadRequestException.class,
                () -> authService.register(request)
        );
    }
}