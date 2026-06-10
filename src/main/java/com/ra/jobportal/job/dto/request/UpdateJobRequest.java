package com.ra.jobportal.job.dto.request;

import com.ra.jobportal.entity.enums.JobType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateJobRequest {

    private String title;

    private String description;

    private String requirement;

    private Long salary;

    private String location;

    private LocalDate deadline;

    private JobType jobType;
}
