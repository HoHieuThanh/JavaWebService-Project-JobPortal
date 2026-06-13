package com.ra.jobportal.application.controller;

import com.ra.jobportal.application.dto.request.UpdateApplicationStatusRequest;
import com.ra.jobportal.application.dto.response.EmployerApplicationResponse;
import com.ra.jobportal.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employer/applications")
@RequiredArgsConstructor
public class EmployerApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/job/{jobId}")
    public Page<EmployerApplicationResponse>
    getApplicationsByJob(@PathVariable Long jobId, Authentication authentication, Pageable pageable) {
        return applicationService.getApplicationsByJob(jobId, authentication.getName(), pageable);
    }

    @GetMapping("/{id}")
    public EmployerApplicationResponse
    getApplicationDetail(@PathVariable Long id, Authentication authentication) {
        return applicationService.getApplicationDetailForEmployer(id, authentication.getName());
    }

    @PutMapping("/{id}/status")
    public EmployerApplicationResponse
    updateStatus(@PathVariable Long id, @RequestBody UpdateApplicationStatusRequest request, Authentication authentication) {
        return applicationService.updateApplicationStatus(id, request, authentication.getName());
    }
}
