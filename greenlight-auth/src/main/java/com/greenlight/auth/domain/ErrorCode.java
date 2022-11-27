package com.greenlight.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATE_MEMBER_FAILED("E001", "이미 가입되어 있는 유저입니다.", HttpStatus.BAD_REQUEST)
    , NOT_FOUND_MEMBER_FAILED("E002", "유저를 찾을수 없습니다.", HttpStatus.BAD_REQUEST)
    , JWT_SIGNATURE_FAILED("E003", "잘못된 JWT 서명입니다.", HttpStatus.UNAUTHORIZED)
    , JWT_EXPIRED_FAILED("E004", "만료된 JWT 토큰입니다.", HttpStatus.UNAUTHORIZED)
    , JWT_UNSUPPORTED_FAILED("E005", "지원되지 않는 JWT 토큰입니다.", HttpStatus.UNAUTHORIZED)
    , JWT_ILLIGAL_ARG_FAILED("E006", "JWT 토큰이 잘못되었습니다.", HttpStatus.UNAUTHORIZED)
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
