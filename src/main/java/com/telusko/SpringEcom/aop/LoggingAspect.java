package com.telusko.SpringEcom.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Joesta
 */

@Component
@Aspect
public class LoggingAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    // 1. returnType, 2 fully-qualified class-name, 3. class-name.method.name, 4 - parameters
    @After("execution(* com.telusko.SpringEcom.services.OrderService.placeOrder(..))")
    public void logOrderTransaction(JoinPoint joinPoint) {
        LOGGER.info("Begin -  Ordering Transaction :: {}", joinPoint.getSignature().getName());
    }

    @Before("execution(* com.telusko.SpringEcom.services.OrderService.getAllOrderResponses(..))")
    public void logAfterFetchingAllOrderResponses(JoinPoint joinPoint) {
        LOGGER.info("Begin -  Fetching all order responses :: {}", joinPoint.getSignature().getName());
    }

    @Before("execution(* com.telusko.SpringEcom.services.OrderService.setOrderRepository(..))")
    public void logWhenOrderPropertyIsSet(JoinPoint joinPoint) {

        LOGGER.info("first argument :: {}", joinPoint.getArgs()[0].toString());
        LOGGER.info("## initializing orderRepository :: {}", joinPoint.getSignature().getName());
    }


}
