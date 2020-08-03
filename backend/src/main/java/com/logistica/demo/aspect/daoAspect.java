package com.logistica.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class daoAspect {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Around("execution(* com.logistica.demo.dao.*.*(..))")
    public Object aroundDAO(ProceedingJoinPoint proceedingJoinPoint) {
        long ini = System.currentTimeMillis();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long fin = System.currentTimeMillis();
        log.info("El objeto {} demoró {} ms",proceedingJoinPoint.getSignature(), fin-ini);
        return result;
    }

}
