package com.space.planetmoonapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Pointcut 1: Log all service method executions
     */
    @Pointcut("execution(* com.space.planetmoonapi.service..*(..))")
    public void serviceLayer() {}

    /**
     * Pointcut 2: Log all controller method executions
     */
    @Pointcut("execution(* com.space.planetmoonapi.controller..*(..))")
    public void controllerLayer() {}

    /**
     * Pointcut 3: Log all repository method executions
     */
    @Pointcut("execution(* com.space.planetmoonapi.repository..*(..))")
    public void repositoryLayer() {}

    /**
     * Before advice for service layer
     */
    @Before("serviceLayer()")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.debug("==> Entering Service Method: {} with arguments: {}", methodName, Arrays.toString(args));
    }

    /**
     * After returning advice for service layer
     */
    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterReturningServiceMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug("<== Service Method {} returned: {}", methodName, result);
    }

    /**
     * After throwing advice for service layer
     */
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "exception")
    public void logAfterThrowingServiceMethod(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("!!! Service Method {} threw exception: {}", methodName, exception.getMessage());
    }

    /**
     * Around advice for controller layer - measures execution time
     */
    @Around("controllerLayer()")
    public Object logAroundControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        log.info(">>> Controller Method {} started", methodName);

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("<<< Controller Method {} completed in {} ms", methodName, executionTime);
            return result;
        } catch (Exception ex) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("<<< Controller Method {} failed after {} ms with error: {}",
                    methodName, executionTime, ex.getMessage());
            throw ex;
        }
    }

    /**
     * Before advice for repository layer
     */
    @Before("repositoryLayer()")
    public void logBeforeRepositoryMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug("--- Executing Repository Method: {}", methodName);
    }
}