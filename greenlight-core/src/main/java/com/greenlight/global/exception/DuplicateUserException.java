package com.greenlight.global.exception;

import com.greenlight.global.domain.ErrorCode;

public class DuplicateUserException extends BusinessException {
    public DuplicateUserException() {
        super(ErrorCode.DUPLICATE_USER_FAILED);
    }
}
