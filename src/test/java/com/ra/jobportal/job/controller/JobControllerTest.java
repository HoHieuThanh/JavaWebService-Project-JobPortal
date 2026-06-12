package com.ra.jobportal.job.controller;

import com.ra.jobportal.job.dto.response.JobResponse;
import com.ra.jobportal.job.service.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobController.class)
@AutoConfigureMockMvc(addFilters = false)
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @Test
    void get_all_jobs_success() throws Exception {

        when(jobService.getApprovedJobs(
                anyString(),
                any(PageRequest.class)
        )).thenReturn(
                new PageImpl<>(Collections.emptyList())
        );

        mockMvc.perform(
                        get("/api/v1/jobs")
                )
                .andExpect(status().isOk());
    }
}