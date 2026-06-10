package com.ra.jobportal.job.service.impl;

import com.ra.jobportal.entity.Job;
import com.ra.jobportal.entity.User;
import com.ra.jobportal.entity.enums.JobStatus;
import com.ra.jobportal.entity.enums.RoleName;
import com.ra.jobportal.job.dto.request.CreateJobRequest;
import com.ra.jobportal.job.dto.request.UpdateJobRequest;
import com.ra.jobportal.job.dto.response.JobResponse;
import com.ra.jobportal.job.repository.JobRepository;
import com.ra.jobportal.job.service.JobService;
import com.ra.jobportal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final UserRepository userRepository;

    @Override
    public JobResponse createJob(
            CreateJobRequest request,
            String username
    ) {

        User employer = userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        if (employer.getRole().getName()
                != RoleName.EMPLOYER) {

            throw new RuntimeException(
                    "Only employer can create job"
            );
        }

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .requirement(request.getRequirement())
                .salary(request.getSalary())
                .location(request.getLocation())
                .deadline(request.getDeadline())
                .jobType(request.getJobType())
                .jobStatus(JobStatus.PENDING)
                .employer(employer)
                .build();

        jobRepository.save(job);

        return convertToResponse(job);
    }

    @Override
    public Page<JobResponse> getMyJobs(String username, Pageable pageable) {
        return jobRepository.findByEmployerUsername(username, pageable).map(this::convertToResponse);
    }

    @Override
    public JobResponse getJobDetail(Long id, String username) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        if (!job.getEmployer().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        return convertToResponse(job);
    }

    @Override
    public JobResponse updateJob(Long id, UpdateJobRequest request, String username) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        if (!job.getEmployer().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        if (request.getTitle() != null) {
            job.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            job.setDescription(request.getDescription());
        }
        if (request.getRequirement() != null) {
            job.setRequirement(request.getRequirement());
        }
        if (request.getSalary() != null) {
            job.setSalary(request.getSalary());
        }
        if (request.getLocation() != null) {
            job.setLocation(request.getLocation());
        }
        if (request.getDeadline() != null) {
            job.setDeadline(request.getDeadline());
        }
        if (request.getJobType() != null) {
            job.setJobType(request.getJobType());
        }
        job.setJobStatus(JobStatus.PENDING);
        jobRepository.save(job);
        return convertToResponse(job);
    }

    @Override
    public void deleteJob(Long id, String username) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        if (!job.getEmployer().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        jobRepository.delete(job);
    }

    @Override
    public Page<JobResponse> getPendingJobs(Pageable pageable) {
        return jobRepository.findByJobStatus(JobStatus.PENDING, pageable)
                .map(this::convertToResponse);
    }

    @Override
    public JobResponse approveJob(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        job.setJobStatus(JobStatus.APPROVED);
        jobRepository.save(job);
        return convertToResponse(job);
    }

    @Override
    public JobResponse rejectJob(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        job.setJobStatus(JobStatus.REJECTED);
        jobRepository.save(job);
        return convertToResponse(job);
    }

    private JobResponse convertToResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirement(job.getRequirement())
                .salary(job.getSalary())
                .location(job.getLocation())
                .deadline(job.getDeadline())
                .jobType(job.getJobType())
                .jobStatus(job.getJobStatus())
                .employerName(job.getEmployer().getFullName())
                .build();
    }
}
