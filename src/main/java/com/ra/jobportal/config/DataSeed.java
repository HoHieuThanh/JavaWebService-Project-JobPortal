package com.ra.jobportal.config;

import com.ra.jobportal.auth.repository.RoleRepository;
import com.ra.jobportal.entity.Role;
import com.ra.jobportal.entity.enums.RoleName;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeed {
    @Bean
    CommandLineRunner initRole(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {

                roleRepository.save(
                        Role.builder()
                                .name(RoleName.ADMIN)
                                .build());

                roleRepository.save(
                        Role.builder()
                                .name(RoleName.EMPLOYER)
                                .build());

                roleRepository.save(
                        Role.builder()
                                .name(RoleName.CANDIDATE)
                                .build());
            }
        };
    }
}
