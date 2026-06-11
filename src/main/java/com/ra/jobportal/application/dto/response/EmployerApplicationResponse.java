package com.ra.jobportal.application.dto.response;

import com.ra.jobportal.entity.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployerApplicationResponse {
    private Long applicationId;
    private Long candidateId;
    private String candidateName;
    private String candidateEmail;
    private String candidatePhone;
    private String cvUrl;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
}