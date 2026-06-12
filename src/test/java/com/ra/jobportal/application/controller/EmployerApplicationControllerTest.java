package com.ra.jobportal.application.controller;

import com.ra.jobportal.application.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployerApplicationController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployerApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationService applicationService;

    @Test
    @WithMockUser(username = "employer")
    void get_applications_by_job_success() throws Exception {

        when(applicationService.getApplicationsByJob(
                anyLong(),
                anyString(),
                any()
        )).thenReturn(
                new PageImpl<>(Collections.emptyList())
        );

        mockMvc.perform(
                        get("/api/v1/employer/applications/job/1")
                )
                .andExpect(status().isOk());
    }
}