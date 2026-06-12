package com.ra.jobportal.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.jobportal.auth.dto.request.LoginRequest;
import com.ra.jobportal.auth.dto.request.RegisterRequest;
import com.ra.jobportal.auth.dto.response.AuthResponse;
import com.ra.jobportal.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @Test
    void login_success() throws Exception {

        when(authService.login(any(LoginRequest.class)))
                .thenReturn(
                        AuthResponse.builder()
                                .accessToken("access")
                                .refreshToken("refresh")
                                .build()
                );

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk());
    }

    @Test
    void register_success() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("candidate");
        request.setEmail("candidate@gmail.com");
        request.setPassword("123456");
        request.setRole("CANDIDATE");

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk());
    }
}