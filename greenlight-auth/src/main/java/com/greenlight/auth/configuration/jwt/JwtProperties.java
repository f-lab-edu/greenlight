package com.greenlight.auth.configuration.jwt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(value = "jwt")
public class JwtProperties {
    @NotBlank
    private final String authorizationHeader;
    @NotBlank
    private final String accessHeader;
    @NotBlank
    private final String refreshHeader;
    @NotBlank
    private final String tokenHeaderPrefix;
    @NotBlank
    private final String secretKey;
    @NotNull
    @Positive
    private final Integer accessTokenValidityInSeconds;
    @NotNull
    @Positive
    private final Integer refreshTokenValidityInSeconds;
}
