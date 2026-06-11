package com.ra.jobportal.job.controller;

import com.ra.jobportal.job.dto.response.JobResponse;
import com.ra.jobportal.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class PublicJobController {

    private final JobService jobService;

    @GetMapping
    public Page<JobResponse> getJobs(@RequestParam(required = false) String keyword, Pageable pageable) {
        return jobService.getApprovedJobs(keyword, pageable);
    }

    @GetMapping("/{id}")
    public JobResponse getJobDetail(@PathVariable Long id) {
        return jobService.getApprovedJobDetail(id);
    }
}