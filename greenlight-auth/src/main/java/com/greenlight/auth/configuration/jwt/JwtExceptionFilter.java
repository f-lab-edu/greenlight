package com.greenlight.auth.configuration.jwt;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.domain.ErrorCode;
import com.greenlight.auth.exception.JwtException;
import com.greenlight.auth.ui.response.ErrorResponse;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException jwte) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, jwte.getErrorCode());
        }
    }

    public void setErrorResponse(HttpStatus httpStatus, HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

		response.getWriter().write(String.valueOf(new ErrorResponse(errorCode)));
    }
}
