package com.telusko.SpringEcom.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Joesta
 */

@Aspect
@Component
public class PerformanceMonitoringAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitoringAspect.class);

    @Around("execution(* com.telusko.SpringEcom.services.OrderService.*(..))")
    public Object monitorTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object object = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Elapsed time for {}:  {} ms", joinPoint.getSignature().getName(), elapsedTime);

        return object;
    }
}
