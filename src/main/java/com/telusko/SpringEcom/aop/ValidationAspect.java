package com.telusko.SpringEcom.aop;

import com.telusko.SpringEcom.exception.CredentialsRequiredException;
import com.telusko.SpringEcom.models.dto.LoginRequest;
import com.telusko.SpringEcom.models.dto.LoginResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Joesta
 */

@Aspect
@Component
public class ValidationAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);

    @Around("execution(* com.telusko.SpringEcom.services.ProductService.getProductById(..)) && args(productId))")
    public Object validateProductId(ProceedingJoinPoint joinPoint, Long productId) throws Throwable {
        if (productId < 0) {
            LOGGER.warn("productId is less than 0... validating productId : {} ", productId);
            productId = Math.abs(productId);
            LOGGER.info("productId is validated: {} ", productId);
        }

        return joinPoint.proceed(new Object[]{productId});
    }

//    @Before("execution(* com.telusko.SpringEcom.services.AuthService.login(..)) && args(req)")
//    public Object validateLoginDetails(ProceedingJoinPoint joinPoint, LoginRequest req) throws Throwable {
//        LOGGER.info("validating login : {}", req);
//        if (req.username() == null || req.username().isEmpty() || req.password() == null || req.password().isEmpty()) {
//            throw new CredentialsRequiredException("Username and password are required");
//        }
//
//        return joinPoint.proceed();
//    }

    @Before("execution(* com.telusko.SpringEcom.services.AuthService.login(..)) && args(req)")
    public void validateLoginDetails(LoginRequest req) {
        LOGGER.info("validating login request");

        if (req == null ||
                req.username() == null || req.username().trim().isEmpty() ||
                req.password() == null || req.password().trim().isEmpty()) {

            throw new CredentialsRequiredException("Username and password are required");
        }
    }
}
