package com.greenlight.auth.ui;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenlight.auth.application.AuthenticationService;
import com.greenlight.auth.ui.dto.request.LoginRequest;
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

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(
            @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.generateTokenDTO(loginRequest));
    }
}
