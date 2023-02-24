package com.greenlight.global.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import com.greenlight.global.domain.auth.Auth;
import com.greenlight.global.infrastructure.config.security.jwt.TokenProvider;

@Configuration
public class DomainConfig {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public DomainConfig(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Bean
    public Auth auth() {
        return new Auth(tokenProvider, authenticationManagerBuilder);
    }
}
