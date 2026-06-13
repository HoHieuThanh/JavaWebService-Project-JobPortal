package com.ra.jobportal.user.service;

import com.ra.jobportal.user.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    Page<UserResponse> getUsers(String keyword, Pageable pageable);
    void deactivateUser(Long id);
    void activateUser(Long id);
    String uploadCv(MultipartFile file, String username);
    UserResponse getUserDetail(Long id);
    void deleteUser(Long id);
}