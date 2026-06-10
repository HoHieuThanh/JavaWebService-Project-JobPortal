package com.ra.jobportal.job.service;

import com.ra.jobportal.job.dto.request.CreateJobRequest;
import com.ra.jobportal.job.dto.request.UpdateJobRequest;
import com.ra.jobportal.job.dto.response.JobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {
    JobResponse createJob(CreateJobRequest request, String username);
    Page<JobResponse> getMyJobs(String username, Pageable pageable);
    JobResponse getJobDetail(Long id, String username);
    JobResponse updateJob(Long id, UpdateJobRequest request, String username);
    void deleteJob(Long id, String username);
    Page<JobResponse> getPendingJobs(Pageable pageable);
    JobResponse approveJob(Long id);
    JobResponse rejectJob(Long id);
}
