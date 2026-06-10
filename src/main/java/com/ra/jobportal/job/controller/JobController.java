package com.ra.jobportal.job.controller;

import com.ra.jobportal.job.dto.request.CreateJobRequest;
import com.ra.jobportal.job.dto.request.UpdateJobRequest;
import com.ra.jobportal.job.dto.response.JobResponse;
import com.ra.jobportal.job.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employer/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public JobResponse createJob(@Valid @RequestBody CreateJobRequest request, Authentication authentication) {
        return jobService.createJob(request, authentication.getName());
    }

    @GetMapping
    public Page<JobResponse> getMyJobs(Authentication authentication, Pageable pageable) {
        return jobService.getMyJobs(authentication.getName(), pageable);
    }

    @GetMapping("/{id}")
    public JobResponse getDetail(@PathVariable Long id, Authentication authentication) {
        return jobService.getJobDetail(id, authentication.getName());
    }

    @PutMapping("/{id}")
    public JobResponse updateJob(@PathVariable Long id, @RequestBody UpdateJobRequest request, Authentication authentication) {
        return jobService.updateJob(id, request, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id, Authentication authentication) {
        jobService.deleteJob(id, authentication.getName());
        return "Delete success";
    }
}