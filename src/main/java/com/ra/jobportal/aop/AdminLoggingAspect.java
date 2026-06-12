package com.ra.jobportal.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AdminLoggingAspect {
    @AfterReturning("execution(* com.ra.jobportal.auth.service.impl.AuthServiceImpl.logout(..))")
    public void logLogout() {
        log.info("User logout success");
    }

    @AfterReturning("execution(* com.ra.jobportal.job.service.impl.JobServiceImpl.rejectJob(..))")
    public void logRejectJob() {
        log.info("Admin rejected job");
    }

    @AfterThrowing(
            pointcut = "execution(* com.ra.jobportal..service..*(..))",
            throwing = "exception"
    )
    public void logException(Exception exception) {
        log.error(exception.getMessage());
    }
}
