package com.greenlight.auth.configuration.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtProperties jwtProperties;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder.addFilterBefore(new JwtFilter(jwtTokenProvider, jwtProperties), UsernamePasswordAuthenticationFilter.class);
        builder.addFilterBefore(jwtExceptionFilter, JwtFilter.class);
    }
}
