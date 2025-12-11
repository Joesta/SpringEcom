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
}
