package com.example.bookcatalog.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {
    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());
    @Pointcut("within(com.example.bookcatalog.controller.*)")
    public void stringProcessingMethods() {
    }

    @After("stringProcessingMethods()")
    public void logControllerMethodCall(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        String className = jp.getSignature().getDeclaringTypeName().substring(35);
        logger.log(Level.INFO, "название контроллера: "+className+ ", название метода: " + methodName);
    }
}
