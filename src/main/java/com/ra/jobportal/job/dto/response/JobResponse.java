package com.ra.jobportal.job.dto.response;

import com.ra.jobportal.entity.enums.JobStatus;
import com.ra.jobportal.entity.enums.JobType;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
    public class JobResponse {

    private Long id;

    private String title;

    private String description;

    private String requirement;

    private Long salary;

    private String location;

    private LocalDate deadline;

    private JobType jobType;

    private JobStatus jobStatus;

    private String employerName;
}
