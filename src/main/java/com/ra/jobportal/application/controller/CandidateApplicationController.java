package com.ra.jobportal.application.controller;

import com.ra.jobportal.application.dto.response.ApplicationResponse;
import com.ra.jobportal.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/candidate/applications")
@RequiredArgsConstructor
public class CandidateApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/jobs/{jobId}")
    public ApplicationResponse applyJob(@PathVariable Long jobId, Authentication authentication) {
        return applicationService.applyJob(jobId, authentication.getName());
    }

    @GetMapping
    public Page<ApplicationResponse>
    getMyApplications(Authentication authentication, Pageable pageable) {
        return applicationService.getMyApplications(
                        authentication.getName(),
                        pageable
                );
    }

    @GetMapping("/{id}")
    public ApplicationResponse
    getApplicationDetail(@PathVariable Long id, Authentication authentication) {
        return applicationService.getApplicationDetail(id, authentication.getName());
    }
}