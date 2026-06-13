package com.ra.jobportal.application.service;

import com.ra.jobportal.application.dto.request.UpdateApplicationStatusRequest;
import com.ra.jobportal.application.dto.response.ApplicationResponse;
import com.ra.jobportal.application.dto.response.EmployerApplicationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {
    ApplicationResponse applyJob(Long jobId, String username);
    Page<ApplicationResponse> getMyApplications(String username, Pageable pageable);
    ApplicationResponse getApplicationDetail(Long id, String username);
    Page<EmployerApplicationResponse> getApplicationsByJob(
            Long jobId,
            String employerUsername,
            Pageable pageable
    );

    EmployerApplicationResponse getApplicationDetailForEmployer(Long id, String employerUsername);
    EmployerApplicationResponse updateApplicationStatus(
            Long applicationId,
            UpdateApplicationStatusRequest request,
            String employerUsername
    );
}