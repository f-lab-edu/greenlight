package com.greenlight.global.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATE_USER_FAILED("E001", "이미 가입되어 있는 유저입니다.", HttpStatus.BAD_REQUEST)
    , NOT_FOUND_USER_FAILED("E002", "유저를 찾을수 없습니다.", HttpStatus.BAD_REQUEST)
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
