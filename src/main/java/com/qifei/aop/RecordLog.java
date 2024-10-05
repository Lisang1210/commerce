package com.qifei.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.qifei.util.Result;

@Aspect
public class RecordLog {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Pointcut("execution(* com.qifei.service.Impl.*(..))")
    public void logPointcut() {}

    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void afterReturningLog(JoinPoint joinPoint, Object result) {
        // 记录操作成功日志
        String operation = joinPoint.getSignature().getName();
        JSONPObject jsonpObject = new JSONPObject("callback", result);
        Result result2=(Result)jsonpObject;
        String message = result2.getMessage();
        // 发送日志消息到RabbitMQ
        rabbitTemplate.convertAndSend("log.exchange", "*.log.routing.key", message);
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "exception")
    public void afterThrowingLog(JoinPoint joinPoint, Throwable exception) {
        // 记录操作失败日志
        String operation = joinPoint.getSignature().getName();
        JSONPObject jsonpObject = new JSONPObject("callback", result);
        Result result2=(Result)jsonpObject;
        String message = result2.getMessage()+"原因：" + exception.getMessage();
        // 发送日志消息到RabbitMQ
        rabbitTemplate.convertAndSend("log.exchange", "*.log.routing.key", message);
    }
}
