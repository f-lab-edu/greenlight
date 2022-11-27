package com.greenlight.auth.exception;

import com.greenlight.auth.domain.ErrorCode;

public class NotFoundUserException extends BusinessException {
    public NotFoundUserException() {
        super(ErrorCode.NOT_FOUND_MEMBER_FAILED);
    }
}
