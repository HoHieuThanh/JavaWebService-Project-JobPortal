package com.ra.jobportal.application.dto.response;

import com.ra.jobportal.entity.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String employerName;
    private ApplicationStatus status;
    private String cvUrl;
    private LocalDateTime appliedAt;
}