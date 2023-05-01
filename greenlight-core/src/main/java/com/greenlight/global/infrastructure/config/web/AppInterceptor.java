package com.greenlight.global.infrastructure.config.web;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AppInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("================== START ==================");
        String traceId = request.getHeader("traceId");
        log.info("header traceId : {}", traceId);
        if (!StringUtils.hasText(traceId)) {
            traceId = UUID.randomUUID().toString();
            log.info("UUID traceId : {}", traceId);
        }

        MDC.put("traceId", traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("================== END ==================");
        log.info("end traceId : {}", MDC.get("traceId"));
        MDC.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
