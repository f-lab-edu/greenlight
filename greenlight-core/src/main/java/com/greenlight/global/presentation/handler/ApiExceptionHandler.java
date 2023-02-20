package com.greenlight.global.presentation.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.greenlight.global.domain.model.constants.common.ResponseCommonCode;
import com.greenlight.global.infrastructure.exception.MemberAlreadySignUpMemberIdException;
import com.greenlight.global.infrastructure.exception.MemberIdNotFoundException;
import com.greenlight.global.infrastructure.exception.MemberNotActivatedException;
import com.greenlight.global.presentation.response.ApiResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * CustomException
     */
    @ExceptionHandler(MemberAlreadySignUpMemberIdException.class)
    public ApiResponse<Object> handler(MemberAlreadySignUpMemberIdException e) {
        return ApiResponse.fail(
                new ApiResponse.Error(
                        ResponseCommonCode.FAILED_MEMBER_ALREADY_SIGNUP.getCode(),
                        ResponseCommonCode.FAILED_MEMBER_ALREADY_SIGNUP.getMessage()
                )
        );
    }

    @ExceptionHandler(MemberIdNotFoundException.class)
    public ApiResponse<Object> handler(MemberIdNotFoundException e) {
        return ApiResponse.fail(
                new ApiResponse.Error(
                        ResponseCommonCode.FAILED_MEMBER_NOT_FOUND.getCode(),
                        ResponseCommonCode.FAILED_MEMBER_NOT_FOUND.getMessage()
                )
        );
    }

    @ExceptionHandler(MemberNotActivatedException.class)
    public ApiResponse<Object> handler(MemberNotActivatedException e) {
        return ApiResponse.fail(
                new ApiResponse.Error(
                        ResponseCommonCode.FAILED_MEMBER_NOT_ACTIVATED.getCode(),
                        ResponseCommonCode.FAILED_MEMBER_NOT_ACTIVATED.getMessage()
                )
        );
    }

    /**
     * BindException
     */
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> handler(BindException bindException) {
        List<ApiResponse.Error.ValidationError> validationErrorList = bindException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ApiResponse.Error.ValidationError::of)
                .collect(Collectors.toList());

        return ApiResponse.fail(
                new ApiResponse.Error(
                        ResponseCommonCode.FAILED_INVALID_PARAMETER.getCode(),
                        ResponseCommonCode.FAILED_INVALID_PARAMETER.getMessage(),
                        validationErrorList
                )
        );
    }

    /**
     * Exception
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handler(Exception e) {
        return ApiResponse.fail(
                new ApiResponse.Error(
                        ResponseCommonCode.FAILED_SYSTEM.getCode(),
                        ResponseCommonCode.FAILED_SYSTEM.getMessage()
                )
        );
    }

}
