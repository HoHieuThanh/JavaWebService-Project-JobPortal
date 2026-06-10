package com.ra.jobportal.user.dto.response;

import com.ra.jobportal.entity.enums.RoleName;
import com.ra.jobportal.entity.enums.UserStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private String fullName;

    private String phone;

    private UserStatus status;

    private RoleName role;
}