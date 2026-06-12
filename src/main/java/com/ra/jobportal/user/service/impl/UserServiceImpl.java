package com.ra.jobportal.user.service.impl;

import com.ra.jobportal.entity.User;
import com.ra.jobportal.entity.enums.UserStatus;
import com.ra.jobportal.exception.BadRequestException;
import com.ra.jobportal.exception.ResourceNotFoundException;
import com.ra.jobportal.user.dto.response.UserResponse;
import com.ra.jobportal.user.repository.UserRepository;
import com.ra.jobportal.user.service.UserService;
import com.ra.jobportal.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
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