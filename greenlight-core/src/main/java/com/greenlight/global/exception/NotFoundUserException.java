package com.greenlight.global.exception;

import com.greenlight.global.domain.ErrorCode;

public class NotFoundUserException extends BusinessException {
    public NotFoundUserException() {
        super(ErrorCode.NOT_FOUND_USER_FAILED);
    }
}
