package com.greenlight.auth.exception;

import com.greenlight.auth.domain.ErrorCode;

public class ConvertToObjectException extends BusinessException {
    public ConvertToObjectException(String message) {
    	super(message, ErrorCode.CONVERTING_FAILED);
    }
}
