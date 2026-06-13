package com.ra.jobportal.application.repository;

import com.ra.jobportal.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ra.jobportal.entity.User;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
    Page<Application> findByCandidateUsername(String username, Pageable pageable);
    Page<Application> findByJobId(Long jobId, Pageable pageable);

    void deleteByCandidate(User candidate);
    void deleteByJobIdIn(List<Long> jobIds);
}