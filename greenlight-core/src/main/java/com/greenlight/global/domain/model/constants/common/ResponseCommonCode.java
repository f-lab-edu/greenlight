package com.greenlight.global.domain.model.constants.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCommonCode {

    SUCCESS("S001", "성공", HttpStatus.OK),

    FAILED_SYSTEM("E001", "시스템 에러가 발생하였습니다.", HttpStatus.BAD_REQUEST),
    FAILED_MEMBER_ALREADY_SIGNUP("E002", "이미 가입되어 있는 회원입니다.", HttpStatus.BAD_REQUEST),
    FAILED_MEMBER_NOT_FOUND("E003", "회원ID를 찾을수가 없습니다.", HttpStatus.BAD_REQUEST),
    FAILED_MEMBER_NOT_ACTIVATED("E004", "회원이 활성화상태가 아닙니다.", HttpStatus.BAD_REQUEST),
    FAILED_INVALID_PARAMETER("E005", "유효하지 않는 파라미터입니다.", HttpStatus.BAD_REQUEST),

    FAILED_INVALID_JWT_SIGNATURE("E030", "잘못된 JWT 서명입니다.", HttpStatus.BAD_REQUEST),
    FAILED_EXPIRED_JWT_TOKEN("E031", "만료된 JWT 토큰입니다.", HttpStatus.BAD_REQUEST),
    FAILED_UNSUPPORTED_JWT_TOKEN("E032", "지원되지 않는 JWT 토큰입니다.", HttpStatus.BAD_REQUEST),
    FAILED_ILLEGALARGUMENT_JWT_TOKEN("E033", "JWT 토큰이 잘못되었습니다.", HttpStatus.BAD_REQUEST),

    FAILED_ROLE_PERMISSIONS("E040", "접근 권한이 없습니다.", HttpStatus.BAD_REQUEST)
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
