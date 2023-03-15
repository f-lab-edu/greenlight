package com.greenlight.global.infrastructure.config;

import com.greenlight.global.domain.auth.Auth;
import com.greenlight.global.domain.pwd.PasswordHelper;
import com.greenlight.global.infrastructure.config.security.jwt.TokenProvider;
import com.greenlight.global.infrastructure.pwd.PasswordHelperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainConfig {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final PasswordEncoder passwordEncoder;

	public DomainConfig(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, PasswordEncoder passwordEncoder) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
    public Auth auth() {
        return new Auth(tokenProvider, authenticationManagerBuilder);
    }

    @Bean
	public PasswordHelper passwordHelper() {
		return new PasswordHelperImpl(passwordEncoder);
	}
}
