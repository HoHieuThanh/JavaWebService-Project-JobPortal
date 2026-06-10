package com.ra.jobportal.admin.controller;

import com.ra.jobportal.job.dto.response.JobResponse;
import com.ra.jobportal.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/jobs")
@RequiredArgsConstructor
public class AdminJobController {

    private final JobService jobService;

    @GetMapping("/pending")
    public Page<JobResponse> getPendingJobs(
            Pageable pageable
    ) {

        return jobService.getPendingJobs(
                pageable
        );
    }

    @PutMapping("/{id}/approve")
    public JobResponse approveJob(
            @PathVariable Long id
    ) {

        return jobService.approveJob(id);
    }

    @PutMapping("/{id}/reject")
    public JobResponse rejectJob(
            @PathVariable Long id
    ) {

        return jobService.rejectJob(id);
    }
}