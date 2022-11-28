package com.greenlight.auth.configuration.jwt;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.greenlight.auth.domain.ErrorCode;
import com.greenlight.auth.domain.ObjectJsonConverter;
import com.greenlight.auth.exception.JwtException;
import com.greenlight.auth.ui.dto.response.ErrorResponse;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
			log.info("JwtExceptionFilter####");
            filterChain.doFilter(request, response);
        } catch (JwtException jwte) {
            log.info("#### JwtException {}", jwte.getErrorCode());
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, jwte.getErrorCode());
        }
    }

    public void setErrorResponse(HttpStatus httpStatus, HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

		response.getWriter().write(ObjectJsonConverter.convertToJson(new ErrorResponse(errorCode)));
    }
}
