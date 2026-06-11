package com.ra.jobportal.application.repository;

import com.ra.jobportal.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
    Page<Application> findByCandidateUsername(String username, Pageable pageable);
    Page<Application> findByJobId(Long jobId, Pageable pageable);
}