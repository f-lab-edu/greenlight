package com.greenlight.auth.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenlight.auth.application.AuthenticationService;
import com.greenlight.auth.configuration.jwt.JwtProperties;
import com.greenlight.auth.ui.dto.request.LoginRequest;
import com.greenlight.auth.ui.dto.request.TokenRequest;
import com.greenlight.auth.ui.dto.response.TokenResponse;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    /**
     * userId, password
     */
    @PostMapping("/create-token")
    public ResponseEntity<TokenResponse> createToken(
            @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.generateTokenDTO(loginRequest));
    }

	/**
	 * Authorization 헤더명칭은 표준이긴 하나 필수는 아니므로 변경가능함 근데 어떤 문제?? 봤는데 까먹음
	 */
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(
		@RequestHeader("Authorization") String access_token
		, @RequestHeader("refresh_token") String refresh_token
		, @RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshTokenDTO(tokenRequest));

    }
}
