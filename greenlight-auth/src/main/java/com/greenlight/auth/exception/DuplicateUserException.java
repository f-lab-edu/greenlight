package com.greenlight.auth.exception;

import com.domain.ErrorCode;

public class DuplicateUserException extends BusinessException {
    public DuplicateUserException() {
        super(ErrorCode.DUPLICATE_MEMBER_FAILED);
    }
}
