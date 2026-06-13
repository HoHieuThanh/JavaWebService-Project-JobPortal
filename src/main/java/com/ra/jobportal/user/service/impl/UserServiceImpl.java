package com.ra.jobportal.user.service.impl;

import com.ra.jobportal.entity.User;
import com.ra.jobportal.entity.Job;
import com.ra.jobportal.entity.enums.UserStatus;
import com.ra.jobportal.exception.BadRequestException;
import com.ra.jobportal.exception.ResourceNotFoundException;
import com.ra.jobportal.user.dto.response.UserResponse;
import com.ra.jobportal.user.repository.UserRepository;
import com.ra.jobportal.user.service.UserService;
import com.ra.jobportal.util.FileUploadUtil;
import com.ra.jobportal.auth.repository.RefreshTokenRepository;
import com.ra.jobportal.auth.repository.PasswordResetTokenRepository;
import com.ra.jobportal.application.repository.ApplicationRepository;
import com.ra.jobportal.job.repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public Page<UserResponse> getUsers(
            String keyword,
            Pageable pageable
    ) {

        Page<User> users;

        if (keyword == null || keyword.isBlank()) {

            users = userRepository.findAll(pageable);

        } else {

            users = userRepository
                    .findByUsernameContainingIgnoreCase(
                            keyword,
                            pageable
                    );
        }

        return users.map(this::convertToResponse);
    }

    @Override
    public void deactivateUser(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found"
                        )
                );

        user.setStatus(UserStatus.INACTIVE);

        userRepository.save(user);
    }

    @Override
    public void activateUser(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found"
                        )
                );
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }
    @Override
    @Transactional
    public String uploadCv(
            MultipartFile file,
            String username
    ) {

        if (file.isEmpty()) {

            throw new BadRequestException(
                    "File không được để trống"
            );
        }

        String contentType =
                file.getContentType();

        if (
                contentType == null
                        || !contentType.equals(
                        "application/pdf"
                )
        ) {

            throw new BadRequestException(
                    "Chỉ chấp nhận file PDF"
            );
        }

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User không tồn tại"
                                )
                        );

        String fileName =
                FileUploadUtil.saveFile(
                        file,
                        uploadDir
                );

        user.setCvUrl(fileName);

        userRepository.save(user);

        return fileName;
    }

    @Override
    public UserResponse getUserDetail(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new com.ra.jobportal.exception.ResourceNotFoundException(
                                "User not found"
                        )
                );

        return convertToResponse(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new com.ra.jobportal.exception.ResourceNotFoundException(
                                "User not found"
                        )
                );

        // Delete refresh tokens
        refreshTokenRepository.deleteByUser(user);

        // Delete password reset token
        passwordResetTokenRepository.deleteByUser(user);

        // Delete applications where candidate = user
        applicationRepository.deleteByCandidate(user);

        // If user is employer, delete applications for jobs owned by user first, then delete jobs
        List<Job> jobs = jobRepository.findByEmployer(user);
        if (jobs != null && !jobs.isEmpty()) {
            List<Long> jobIds = jobs.stream().map(Job::getId).collect(Collectors.toList());
            applicationRepository.deleteByJobIdIn(jobIds);
            jobRepository.deleteAll(jobs);
        }

        userRepository.delete(user);
    }

    private UserResponse convertToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .status(user.getStatus())
                .role(user.getRole().getName())
                .build();
    }
}