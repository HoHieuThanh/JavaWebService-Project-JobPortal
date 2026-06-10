package com.ra.jobportal.user.service.impl;

import com.ra.jobportal.entity.User;
import com.ra.jobportal.entity.enums.UserStatus;
import com.ra.jobportal.user.dto.response.UserResponse;
import com.ra.jobportal.user.repository.UserRepository;
import com.ra.jobportal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
                        () -> new RuntimeException(
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
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);
    }

    private UserResponse convertToResponse(
            User user
    ) {

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