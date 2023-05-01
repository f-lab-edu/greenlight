package com.greenlight.global.infrastructure.config.web;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@Aspect
public class LogAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object doLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object proceed = proceedingJoinPoint.proceed();
        stopWatch.stop();

        long totalTimeMillis = stopWatch.getTotalTimeMillis();

        log.info("Running Time : {}", String.format("%.2f", (totalTimeMillis / (double) 1000)));

        return proceed;
    }

}
