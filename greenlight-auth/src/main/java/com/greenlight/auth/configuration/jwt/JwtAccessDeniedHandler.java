package com.greenlight.auth.configuration.jwt;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request
                        , HttpServletResponse response
                        , AccessDeniedException accessDeniedException) throws IOException {
        log.info("JwtAccessDeniedHandler #####");
        //필요한 권한이 없이 접근하려 할때 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}