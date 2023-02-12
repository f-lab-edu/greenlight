package com.greenlight.auth.exception;

import com.domain.ErrorCode;

public class JwtException extends BusinessException {
    public JwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
