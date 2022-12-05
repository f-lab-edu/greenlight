package com.greenlight.auth.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenlight.auth.application.CustomUserDetailsService;
import com.greenlight.auth.configuration.jwt.JwtTokenProvider;
import com.greenlight.auth.ui.request.LoginRequest;
import com.greenlight.auth.ui.request.TokenRequest;
import com.greenlight.auth.ui.response.Response;
import com.greenlight.auth.ui.response.TokenResponse;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * userId, password
     */
    @PostMapping("/create-token")
    public Response<TokenResponse> createToken(
            @RequestBody LoginRequest loginRequest) {
        return Response.success(customUserDetailsService.generateTokenDTO(loginRequest));
    }

    @PostMapping("/refresh-token")
    public Response<TokenResponse> refreshToken(
            @RequestHeader("Authorization") String access_token
            , @RequestHeader("refresh_token") String refresh_token
            , @RequestBody TokenRequest tokenRequest) {
        return Response.success(customUserDetailsService.refreshTokenDTO(access_token, refresh_token));
    }
}
