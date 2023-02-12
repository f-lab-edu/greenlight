package com.greenlight.auth.exception;

import com.greenlight.auth.domain.ErrorCode;

public class JwtException extends BusinessException {
    public JwtException(ErrorCode errorCode) {
        super(errorCode);
    }
}
