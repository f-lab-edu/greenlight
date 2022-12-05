package com.greenlight.auth.exception;

import com.greenlight.auth.ui.response.ErrorResponse;
import com.greenlight.auth.ui.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> businessExceptionHandler(BusinessException e) {
		return ResponseEntity.status(e.getErrorCode().getHttpStatusCode())
			.body(Response.error(new ErrorResponse(e.getErrorCode())));
    }

}
