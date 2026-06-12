package com.ra.jobportal.application.service;

import com.ra.jobportal.application.repository.ApplicationRepository;
import com.ra.jobportal.application.service.impl.ApplicationServiceImpl;
import com.ra.jobportal.entity.*;
import com.ra.jobportal.entity.enums.*;
import com.ra.jobportal.exception.BadRequestException;
import com.ra.jobportal.job.repository.JobRepository;
import com.ra.jobportal.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    @InjectMocks
    private ApplicationServiceImpl service;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobRepository jobRepository;

    @Test
    void apply_job_success() {

        Role role =
                Role.builder()
                        .name(RoleName.CANDIDATE)
                        .build();

        User user =
                User.builder()
                        .id(1L)
                        .username("candidate")
                        .role(role)
                        .build();

        Job job =
                Job.builder()
                        .id(1L)
                        .jobStatus(JobStatus.APPROVED)
                        .build();

        when(userRepository.findByUsername("candidate"))
                .thenReturn(Optional.of(user));

        when(jobRepository.findById(1L))
                .thenReturn(Optional.of(job));

        when(applicationRepository
                .existsByCandidateIdAndJobId(1L,1L))
                .thenReturn(false);

        service.applyJob(1L,"candidate");

        verify(applicationRepository)
                .save(any(Application.class));
    }

    @Test
    void apply_job_duplicate() {

        Role role =
                Role.builder()
                        .name(RoleName.CANDIDATE)
                        .build();

        User user =
                User.builder()
                        .id(1L)
                        .username("candidate")
                        .role(role)
                        .build();

        Job job =
                Job.builder()
                        .id(1L)
                        .jobStatus(JobStatus.APPROVED)
                        .build();

        when(userRepository.findByUsername("candidate"))
                .thenReturn(Optional.of(user));

        when(jobRepository.findById(1L))
                .thenReturn(Optional.of(job));

        when(applicationRepository
                .existsByCandidateIdAndJobId(1L,1L))
                .thenReturn(true);

        assertThrows(
                BadRequestException.class,
                () -> service.applyJob(
                        1L,
                        "candidate"
                )
        );
    }
}