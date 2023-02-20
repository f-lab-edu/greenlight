package com.greenlight.global.presentation.response;

import lombok.Getter;
import lombok.ToString;

import com.greenlight.global.application.processor.MemberSignInProcessor;

@Getter
@ToString
public class SignInResponse {

    private String token;

    public static SignInResponse from(MemberSignInProcessor.Result result) {
        return new SignInResponse(result.getToken());
    }

    public SignInResponse(String token) {
        this.token = token;
    }
}
