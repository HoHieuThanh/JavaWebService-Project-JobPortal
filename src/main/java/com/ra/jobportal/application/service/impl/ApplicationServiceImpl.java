package com.ra.jobportal.application.service.impl;

import com.ra.jobportal.application.dto.request.UpdateApplicationStatusRequest;
import com.ra.jobportal.application.dto.response.ApplicationResponse;
import com.ra.jobportal.application.dto.response.EmployerApplicationResponse;
import com.ra.jobportal.application.repository.ApplicationRepository;
import com.ra.jobportal.application.service.ApplicationService;
import com.ra.jobportal.entity.Application;
import com.ra.jobportal.entity.Job;
import com.ra.jobportal.entity.User;
import com.ra.jobportal.entity.enums.ApplicationStatus;
import com.ra.jobportal.entity.enums.JobStatus;
import com.ra.jobportal.entity.enums.RoleName;
import com.ra.jobportal.exception.BadRequestException;
import com.ra.jobportal.exception.ResourceNotFoundException;
import com.ra.jobportal.exception.UnauthorizedException;
import com.ra.jobportal.job.repository.JobRepository;
import com.ra.jobportal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    @Override
    public ApplicationResponse applyJob(Long jobId, String username) {
        User candidate = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (candidate.getRole().getName() != RoleName.CANDIDATE) {
            throw new BadRequestException(
                    "Only candidate can apply"
            );
        }

        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        if (job.getJobStatus() != JobStatus.APPROVED) {
            throw new ResourceNotFoundException("Job not available");
        }

        if (applicationRepository.existsByCandidateIdAndJobId(candidate.getId(), jobId)) {
            throw new BadRequestException("Already applied");
        }

        Application application =
                Application.builder()
                        .candidate(candidate)
                        .job(job)
                        .cvUrl(candidate.getCvUrl())
                        .status(ApplicationStatus.PENDING)
                        .build();
        applicationRepository.save(application);
        return convertToResponse(application);
    }

    @Override
    public Page<ApplicationResponse> getMyApplications(String username, Pageable pageable) {
        return applicationRepository.findByCandidateUsername(username, pageable)
                .map(this::convertToResponse);
    }

    @Override
    public ApplicationResponse
    getApplicationDetail(Long id, String username) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        if (!application.getCandidate().getUsername().equals(username)) {
            throw new UnauthorizedException("Access denied");
        }
        return convertToResponse(application);
    }

    @Override
    public Page<EmployerApplicationResponse> getApplicationsByJob(Long jobId, String employerUsername, Pageable pageable) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        if (!job.getEmployer().getUsername().equals(employerUsername)) {
            throw new UnauthorizedException("Access denied");
        }

        return applicationRepository.findByJobId(jobId, pageable)
                .map(this::convertToEmployerResponse);
    }

    @Override
    public EmployerApplicationResponse
    updateApplicationStatus(
            Long applicationId,
            UpdateApplicationStatusRequest request,
            String employerUsername
    ) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        Job job = application.getJob();
        if (!job.getEmployer().getUsername().equals(employerUsername)) {
            throw new UnauthorizedException("Access denied");
        }

        if (request.getStatus() == ApplicationStatus.PENDING) {
            throw new BadRequestException("Invalid status");
        }
        application.setStatus(request.getStatus());
        applicationRepository.save(application);
        return convertToEmployerResponse(application);
    }

    @Override
    public EmployerApplicationResponse getApplicationDetailForEmployer(Long id, String employerUsername) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        Job job = application.getJob();
        if (!job.getEmployer().getUsername().equals(employerUsername)) {
            throw new UnauthorizedException("Access denied");
        }
        return convertToEmployerResponse(application);
    }

    private ApplicationResponse convertToResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .jobId(application.getJob().getId())
                .jobTitle(application.getJob().getTitle())
                .employerName(application.getJob().getEmployer().getFullName())
                .status(application.getStatus())
                .cvUrl(application.getCvUrl())
                .appliedAt(application.getAppliedAt())
                .build();
    }
    private EmployerApplicationResponse convertToEmployerResponse(Application application) {
        User candidate = application.getCandidate();
        return EmployerApplicationResponse.builder()
                .applicationId(application.getId())
                .candidateId(candidate.getId())
                .candidateName(candidate.getFullName())
                .candidateEmail(candidate.getEmail())
                .candidatePhone(candidate.getPhone())
                .cvUrl(application.getCvUrl())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .build();
    }
}
