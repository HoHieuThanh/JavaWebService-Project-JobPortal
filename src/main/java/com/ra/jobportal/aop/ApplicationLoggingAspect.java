package com.ra.jobportal.aop;

import com.ra.jobportal.application.dto.response.ApplicationResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ApplicationLoggingAspect {
    @AfterReturning(
            value = "execution(* com.ra.jobportal.application.service.impl.ApplicationServiceImpl.applyJob(..))",
            returning = "result"
    )
    public void logApplyJob(ApplicationResponse result) {
        log.info("Candidate applied jobId={} applicationId={}",
                result.getJobId(),
                result.getId()
        );
    }
}