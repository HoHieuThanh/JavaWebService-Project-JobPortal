package com.ra.jobportal.auth.repository;

import com.ra.jobportal.entity.Role;
import com.ra.jobportal.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}