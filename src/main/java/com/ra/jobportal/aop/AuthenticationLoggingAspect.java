package com.ra.jobportal.aop;

import com.ra.jobportal.auth.dto.response.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuthenticationLoggingAspect {

    @AfterReturning(
            value = "execution(* com.ra.jobportal.auth.service.impl.AuthServiceImpl.login(..))",
            returning = "result"
    )

    public void logLogin(AuthResponse result) {
        log.info("User login success");
    }

    @AfterReturning("execution(* com.ra.jobportal.auth.service.impl.AuthServiceImpl.logout(..))")
    public void logLogout() {
        log.info("User logout success");
    }
}