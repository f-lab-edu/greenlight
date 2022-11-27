package com.greenlight.auth.ui;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.greenlight.auth.exception.BusinessException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public void handle(HttpServletResponse response, BusinessException e) throws IOException {
        log.debug(e.toString());
        response.sendError(e.getHttpStatusCode());
    }

}
