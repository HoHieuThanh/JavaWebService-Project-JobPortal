package com.ra.jobportal.application.controller;

import com.ra.jobportal.application.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CandidateApplicationController.class)
@AutoConfigureMockMvc(addFilters = false)
class CandidateApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationService applicationService;

    @Test
    @WithMockUser(username = "candidate")
    void apply_job_success() throws Exception {

        mockMvc.perform(
                        post("/api/v1/candidate/applications/jobs/1")
                )
                .andExpect(status().isOk());
    }
}