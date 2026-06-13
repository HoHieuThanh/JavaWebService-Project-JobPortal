package com.ra.jobportal.admin.controller;

import com.ra.jobportal.user.dto.response.UserResponse;
import com.ra.jobportal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public Page<UserResponse> getUsers(@RequestParam(required = false) String keyword, Pageable pageable) {
        return userService.getUsers(keyword, pageable);
    }

    @PutMapping("/{id}/deactivate")
    public String deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return "Deactivate success";
    }

    @PutMapping("/{id}/activate")
    public String activateUser(@PathVariable Long id) {
        userService.activateUser(id);
        return "Activate success";
    }

    @GetMapping("/{id}")
    public UserResponse getUserDetail(@PathVariable Long id) {
        return userService.getUserDetail(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "Delete success";
    }
}