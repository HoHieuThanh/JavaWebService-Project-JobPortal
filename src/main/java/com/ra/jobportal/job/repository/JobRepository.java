package com.ra.jobportal.job.repository;

import com.ra.jobportal.entity.Job;
import com.ra.jobportal.entity.enums.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    Page<Job> findByEmployerUsername(String username, Pageable pageable);
    Page<Job> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Job> findByJobStatus(JobStatus status, Pageable pageable);
    Page<Job> findByJobStatusAndTitleContainingIgnoreCase(
            JobStatus status,
            String keyword,
            Pageable pageable
    );

}