package com.ra.jobportal.job.dto.request;
import com.ra.jobportal.entity.enums.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateJobRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String requirement;

    @NotNull
    private Long salary;

    private String location;

    @NotNull
    private LocalDate deadline;

    @NotNull
    private JobType jobType;
}
