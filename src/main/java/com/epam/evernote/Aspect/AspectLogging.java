package com.epam.evernote.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;


@Aspect
@Component
@Slf4j
public class AspectLogging {

    @Pointcut("within(com.epam.evernote.service.Implementations.*)")
    public void interceptServiceMethod() {
    }

    @Around("interceptServiceMethod()")
    public Object getAll(ProceedingJoinPoint joinPoint) throws Throwable {
            LocalTime start = LocalTime.now();
            log.info("{} is starting at {}", joinPoint.getSignature(), start);
            Object proceed = joinPoint.proceed();
            log.info("{} returned {} after {}", joinPoint.getSignature(), proceed,
                    Duration.between(LocalTime.now(), start));
        return proceed;
    }
}

