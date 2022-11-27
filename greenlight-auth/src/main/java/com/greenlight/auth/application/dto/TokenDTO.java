package com.greenlight.auth.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class TokenDTO {
    private final String accessToken;
    private final String refreshToken;
    private final String tokenType;
    private final long expiresIn;
}
