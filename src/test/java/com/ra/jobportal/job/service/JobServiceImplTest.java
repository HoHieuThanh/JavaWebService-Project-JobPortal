package com.ra.jobportal.job.service;

import com.ra.jobportal.entity.*;
import com.ra.jobportal.entity.enums.RoleName;
import com.ra.jobportal.job.dto.request.CreateJobRequest;
import com.ra.jobportal.job.repository.JobRepository;
import com.ra.jobportal.job.service.impl.JobServiceImpl;
import com.ra.jobportal.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    @InjectMocks
    private JobServiceImpl service;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void create_job_success() {

        Role role =
                Role.builder()
                        .name(RoleName.EMPLOYER)
                        .build();

        User employer =
                User.builder()
                        .username("employer")
                        .role(role)
                        .build();

        when(userRepository.findByUsername("employer"))
                .thenReturn(Optional.of(employer));

        CreateJobRequest request =
                new CreateJobRequest();

        request.setTitle("Java");

        service.createJob(
                request,
                "employer"
        );

        verify(jobRepository)
                .save(any(Job.class));
    }
}