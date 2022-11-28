package com.greenlight.auth.exception;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.greenlight.auth.exception.BusinessException;
import com.greenlight.auth.ui.dto.response.ErrorResponse;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handle(HttpServletResponse response, BusinessException e) throws IOException {
		return ResponseEntity.ok(new ErrorResponse(e.getErrorCode()));
    }

}
