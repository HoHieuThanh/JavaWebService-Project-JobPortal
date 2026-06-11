package com.ra.jobportal.auth.repository;

import com.ra.jobportal.entity.RefreshToken;
import com.ra.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    void deleteByUserUsername(String username);
}