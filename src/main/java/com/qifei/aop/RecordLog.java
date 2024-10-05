package com.qifei.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RecordLog {
    @Pointcut("execution(* com.qifei.service.Impl.*(..))")
    public void logPointcut() {}

    @AfterReturning("logPointcut()")
    public void afterLog(ProceedingJoinPoint joinPoint) {

    }

}
